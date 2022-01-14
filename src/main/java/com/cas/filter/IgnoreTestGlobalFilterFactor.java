package com.cas.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class IgnoreTestGlobalFilterFactor extends AbstractGatewayFilterFactory<IgnoreTestGlobalFilterFactor.Config> {

    /**
     * IS_OPEN_KEY
     */
    public static final String IS_OPEN_KEY = "isOpen";

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(IS_OPEN_KEY);
    }

    public IgnoreTestGlobalFilterFactor() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (config.isOpen) {
                exchange.getAttributes().put(SignFilter.SIGN_GLOBAL_FILTER, true);
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        boolean isOpen = false;

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }
    }

    @Override
    public String name() {
        return "IgnoreTestGlobalFilter";
    }
}