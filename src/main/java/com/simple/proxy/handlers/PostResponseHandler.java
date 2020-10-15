package com.simple.proxy.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
    boolean matches(@NonNull HttpServletRequest request,
                    @Nullable String body,
                    @Nullable Map<String, String> headers);

    /**
     * Выполняет POST-запрос.
     *
     * @param request - запрос
     * @param body    - тело сообщения
     * @param headers - словарь заголовков
     * @return ответ
     */
    @Nullable
    ResponseEntity<String> post(@NonNull HttpServletRequest request,
                                @Nullable String body,
                                @Nullable Map<String, String> headers);
}
