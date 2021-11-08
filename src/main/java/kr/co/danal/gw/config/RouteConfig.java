package kr.co.danal.gw.config;

import kr.co.danal.gw.config.properties.RoutingProperties;
import kr.co.danal.gw.filter.AuthorizationFilterFactory;
import kr.co.danal.gw.filter.global.GlobalFilter;
import static kr.co.danal.gw.util.FunctionUtil.returnCodeMappingFunction;
import static kr.co.danal.gw.util.FunctionUtil.authMappingFunction;
import static kr.co.danal.gw.util.FunctionUtil.maptoStringFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {

    private final RoutingProperties routingProperties;

    private final GlobalFilter globalFilter;

    private final AuthorizationFilterFactory authorizationFilterFactory;

    @Bean
    public RouteLocator ipnRoutes(RouteLocatorBuilder builder) {

        return builder.routes()
            .route(routingProperties.getIpn().getId(), r -> r
                    .method(routingProperties.getIpn().getMethod()).and().path(routingProperties.getIpn().getPath())
                    .filters(gatewayFilterSpec -> gatewayFilterSpec
                            // Request
                            .filter(globalFilter.apply(globalFilter.newConfig()))
                            .filter(authorizationFilterFactory.apply(authorizationFilterFactory.newConfig()))
                            .modifyRequestBody(Map.class, Map.class, authMappingFunction()) // auth 매핑
                            .modifyRequestBody(Map.class, String.class, MediaType.APPLICATION_FORM_URLENCODED_VALUE, maptoStringFunction()) // map -> String

                            // Response
                            .modifyResponseBody(String.class, Map.class, returnCodeMappingFunction()) // returnCode 매핑
                            .setResponseHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            )
                    .uri(routingProperties.getIpn().getUri()))
                .build();
    }

}
