package com.simple.proxy.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
    boolean matches(@NonNull HttpServletRequest request);

    /**
     * Выполняет GET-запрос.
     *
     * @param request - запрос
     * @return ответ
     */
    @Nullable
    ResponseEntity<String> get(@NonNull HttpServletRequest request);
}
