package com.simple.proxy.handlers;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Хендлер обработки входящих запросов
 * Если запрос подходит под условие, возвращется custom ответ
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public interface PostResponseHandler {

    /**
     * Проверка запроса на необходимость вернуть custom ответ
     *
     * @param request - запрос
     * @param body    - тело сообщения
     * @param headers - список заголовков
     * @return true - запрос необходимо обработать в ручную, false - иначе
     */
    boolean matches(HttpServletRequest request,
                    String body,
                    Map<String, String> headers);

    /**
     * @return ответ
     */
    ResponseEntity<String> post();
}
