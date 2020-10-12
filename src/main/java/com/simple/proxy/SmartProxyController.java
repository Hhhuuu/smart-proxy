package com.simple.proxy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
@RestController
public class SmartProxyController {
    private static final String START_WITH_PATH = "";

    private final RequestRouter router;

    public SmartProxyController(RequestRouter router) {
        this.router = router;
    }

    @PostMapping(value = START_WITH_PATH + "/**")
    public ResponseEntity<String> postSmartProxy(HttpServletRequest request,
                                                 @RequestBody(required = false) String body,
                                                 @RequestHeader(required = false) Map<String, String> headers) {
        return router.post(request, body, headers, START_WITH_PATH);
    }

    @GetMapping(value = START_WITH_PATH + "/**")
    public ResponseEntity<String> getSmartProxy(HttpServletRequest request) {
        return router.get(request, START_WITH_PATH);
    }
}
