package com.simple.proxy.handlers;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Хендлер обработки входящих запросов
 * Если запрос подходит под условие, возвращется custom ответ
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public interface GetResponseHandler {
    /**
     * Проверка запроса на необходимость вернуть custom ответ
     *
     * @param request - запрос
     * @return true - запрос необходимо обработать в ручную, false - иначе
     */
    boolean matches(HttpServletRequest request);

    /**
     * @return ответ
     */
    ResponseEntity<String> get();
}
