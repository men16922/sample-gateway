package kr.co.danal.gw.filter.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter baseMessage: {}", request.getRemoteAddress());
            log.info("Global Filter Start: request id -> {}", request.getId());
            log.info("Global Filter End: response code -> {}", response.getStatusCode());
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    @Override
    public Config newConfig() {
        return new Config();
    }

    public static class Config {}
}
