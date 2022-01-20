package com.cas.config.filter;

import com.cas.filter.CustomGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/1/11 4:57 下午
 * @desc 自定义过滤器配置类：如果现有过滤器不能满足自己，可以自己做骚操作
 */
@Configuration
public class GatewayRoutesConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes().route(r -> r.
                path("/**")
                .uri("lb://nacos-payment-provider")
                .filters(new CustomGatewayFilter())
                .id("nacos-test"))
                .build();
    }



}
