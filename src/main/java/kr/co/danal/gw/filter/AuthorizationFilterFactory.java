package kr.co.danal.gw.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorizationFilterFactory extends AbstractGatewayFilterFactory<AuthorizationFilterFactory.Config> {

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("===================================================================");
            log.info("AuthorizationFilter");

            HttpHeaders httpHeaders = exchange.getRequest().getHeaders();

            log.info("HEADERS INFO - {}", httpHeaders.toString());

            String authorization = httpHeaders.getOrEmpty("X-Auth-Token").get(0);

            log.info("CHECK AUTHORIZATION -{} ", authorization);

            /**
             *  authorization check
             */

            return chain.filter(exchange);
        };
    }
    @Override
    public Config newConfig() {
        return new Config();
    }

    public static class Config {}
}
