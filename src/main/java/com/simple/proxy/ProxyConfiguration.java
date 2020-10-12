package com.simple.proxy;

import com.simple.proxy.handlers.GetResponseHandler;
import com.simple.proxy.handlers.PostResponseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
@Configuration
public class ProxyConfiguration {

    @Bean
    public PostResponseHandler postResponseHandler() {
        return new PostResponseHandler() {
            @Override
            public boolean matches(HttpServletRequest request, String body, Map<String, String> headers) {
                return false;
            }

            @Override
            public ResponseEntity<String> post() {
                return null;
            }
        };
    }

    @Bean
    public GetResponseHandler getResponseHandler() {
        return new GetResponseHandler() {
            @Override
            public boolean matches(HttpServletRequest request) {
                return false;
            }

            @Override
            public ResponseEntity<String> get() {
                return null;
            }
        };
    }
}
