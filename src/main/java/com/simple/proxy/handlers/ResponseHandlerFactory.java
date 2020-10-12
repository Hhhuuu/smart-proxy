package com.simple.proxy.handlers;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Фабрика с хендлерами
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
@Component
public class ResponseHandlerFactory {
    private List<PostResponseHandler> postHandlers = new ArrayList<>();
    private List<GetResponseHandler> getHandlers = new ArrayList<>();

    public PostResponseHandler getHandler(HttpServletRequest request, String body, Map<String, String> headers) {
        return postHandlers.stream()
                .filter(item -> item.matches(request, body, headers))
                .findFirst()
                .orElse(null);
    }

    public GetResponseHandler getHandler(HttpServletRequest request) {
        return getHandlers.stream()
                .filter(item -> item.matches(request))
                .findFirst()
                .orElse(null);
    }
}
