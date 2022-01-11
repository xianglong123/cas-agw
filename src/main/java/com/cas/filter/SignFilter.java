package com.cas.filter;

import com.alibaba.fastjson.JSONObject;
import com.cas.config.AgwProperties;
import com.cas.config.advice.BusinessException;
import com.cas.enums.CodeAndMsg;
import com.cas.utils.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.cas.utils.RSAUtils.SHA1withRSA;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/1/11 6:35 下午
 * @desc
 */
@Component
public class SignFilter implements GlobalFilter, Ordered {

    @Autowired
    private AgwProperties agwProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (request.getMethod() == HttpMethod.POST) {
            AtomicReference<String> requestBody = new AtomicReference<>("");
            RecorderServerHttpRequestDecorator requestDecorator = new RecorderServerHttpRequestDecorator(request);
            Flux<DataBuffer> body = requestDecorator.getBody();
            body.subscribe(buffer -> {
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
                requestBody.set(charBuffer.toString());
            });
            // 获取body参数
            JSONObject jsonObject = JSONObject.parseObject(requestBody.get());
            String json = jsonObject.toJSONString();
            // todo2 做自己的校验
            List<String> signs = requestDecorator.getHeaders().get("sign");
            if (signs == null || signs.isEmpty()) {
                throw new BusinessException(CodeAndMsg.SIGN_IS_BLANK);
            } else {
                try {
                    boolean verify = RSAUtils.verify(RSAUtils.getPublicKey(agwProperties.getPublicKey()), json, signs.get(0), SHA1withRSA);
                    if (!verify) {
                        throw new BusinessException(CodeAndMsg.SIGN_IS_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BusinessException(CodeAndMsg.SIGN_IS_ERROR);
                }
            }
        }

        return chain.filter(exchange);
    }


    public class RecorderServerHttpRequestDecorator extends ServerHttpRequestDecorator {

        private final List<DataBuffer> dataBuffers = new ArrayList<>();

        public RecorderServerHttpRequestDecorator(ServerHttpRequest delegate) {
            super(delegate);
            super.getBody().map(dataBuffer -> {
                dataBuffers.add(dataBuffer);
                return dataBuffer;
            }).subscribe();
        }

        @Override
        public Builder mutate() {
            return null;
        }

        @Override
        public Flux<DataBuffer> getBody() {
            return copy();
        }

        private Flux<DataBuffer> copy() {
            return Flux.fromIterable(dataBuffers)
                    .map(buf -> buf.factory().wrap(buf.asByteBuffer()));
        }
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
