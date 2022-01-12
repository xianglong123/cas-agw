package com.cas.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/1/12 9:47 上午
 * @desc
 */
@Configuration
public class KeyResolverConfiguration {

    /**
     * 路径限流
     * @return
     */
//    @Bean
    public KeyResolver pathKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getURI().getPath());
    }


    /**
     * 参数限流
     * @return
     */
//    @Bean
    public KeyResolver parameterKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("id"));
    }

    /**
     * ip限流
     * @return
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

}
