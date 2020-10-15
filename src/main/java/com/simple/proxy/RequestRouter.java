package com.simple.proxy;

import com.simple.proxy.handlers.GetResponseHandler;
import com.simple.proxy.handlers.PostResponseHandler;
import com.simple.proxy.handlers.ResponseHandlerFactory;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

import static java.util.Optional.ofNullable;

/**
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
@Component
public class RequestRouter {
    private static final Logger log = LoggerFactory.getLogger(RequestRouter.class);
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String URL_FORMAT = "%s://%s:%s/%s";
    private static final String URL_FORMAT_WITHOUT_PORT = "%s://%s/%s";
    private static final String DEFAULT_PROTOCOL = "http";

    private final ProxyProperties properties;
    private final ResponseHandlerFactory handlerFactory;

    public RequestRouter(ProxyProperties properties,
                         ResponseHandlerFactory handlerFactory) {
        this.properties = properties;
        this.handlerFactory = handlerFactory;
    }

    /**
     * Проксирование GET запроса
     *
     * @param request       - запрос
     * @param startWithPath - часть запроса, которое необходимо заменить
     * @return ответ
     */
    @Nullable
    public ResponseEntity<String> get(@NonNull HttpServletRequest request, @NonNull String startWithPath) {
        GetResponseHandler handler = handlerFactory.getHandler(request);
        if (Objects.nonNull(handler)) {
            return handler.get(request);
        }

        String url = prepareQuery(request.getQueryString(), startWithPath);
        return getRestTemplate().getForEntity(getBaseUrl() + url, String.class);
    }

    /**
     * Проксирование POST запроса
     *
     * @param request       - запрос
     * @param body          - тело сообщения
     * @param headers       - список заголовков
     * @param startWithPath - часть запроса, которое необходимо заменить
     * @return ответ
     */
    public ResponseEntity<String> post(HttpServletRequest request,
                                       String body,
                                       Map<String, String> headers,
                                       String startWithPath) {

        PostResponseHandler handler = handlerFactory.getHandler(request, body, headers);
        if (Objects.nonNull(handler)) {
            return handler.post(request, body, headers);
        }

        String url = prepareQuery(request.getServletPath(), startWithPath);
        return getRestTemplate().postForEntity(getBaseUrl() + url, getHttpEntity(body, headers), String.class);
    }

    private String prepareQuery(String query, String startWithPath) {
        if (StringUtils.isNotEmpty(startWithPath)) {
            query = query.replace(startWithPath, StringUtils.EMPTY);
        }
        return query;
    }

    private RestTemplate getRestTemplate() {
        return REST_TEMPLATE;
    }

    private HttpEntity<String> getHttpEntity(String body, Map<String, String> headers) {
        MultiValueMap<String, String> values = new LinkedMultiValueMap<>();
        if (MapUtils.isNotEmpty(headers)) {
            values.setAll(headers);
        }

        return new HttpEntity<>(body, values);
    }

    private String getBaseUrl() {
        int port = properties.getPort();
        boolean isEmptyPort = port == 0;
        if (isEmptyPort) {
            log.warn("Порт не задан либо равен 0. Запросы не будут учитывать его.");
        }

        return String.format(isEmptyPort ? URL_FORMAT_WITHOUT_PORT : URL_FORMAT, ofNullable(properties.getProtocol()).orElse(DEFAULT_PROTOCOL),
                ofNullable(properties.getHost()).orElseThrow(() -> new IllegalArgumentException("Отсутсвует значение настроки host")),
                port,
                ofNullable(properties.getPostfix()).orElse("")
        );
    }
}
