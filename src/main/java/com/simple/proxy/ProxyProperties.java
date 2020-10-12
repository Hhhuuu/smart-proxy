package com.simple.proxy;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Список настроек приложения
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
@Configuration
@ConfigurationProperties(prefix = "proxy")
public class ProxyProperties {
    private String host;
    private String protocol;
    private String postfix;
    private int port;

    /**
     * @return хост на который будут проксироваться запросы
     */
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return порт на который будут проксироваться запросы
     */
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return протокол на который будут проксироваться запросы
     */
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @return постфикс на который будут проксироваться запросы
     */
    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }
}
