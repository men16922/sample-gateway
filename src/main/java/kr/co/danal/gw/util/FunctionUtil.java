package kr.co.danal.gw.util;

import static kr.co.danal.gw.util.FilterUtil.returnCodeMapping;
import static kr.co.danal.gw.util.FilterUtil.authMapping;
import static kr.co.danal.gw.util.FilterUtil.maptoString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
public class FunctionUtil {

    private FunctionUtil(){}

    public static RewriteFunction<Map, Map> authMappingFunction() {
        return (exchange, s) -> Mono.just(authMapping(s));
    }

    public static RewriteFunction<Map, String> maptoStringFunction() {
        return (exchange, s) -> {
            try {
                return Mono.just(maptoString(s));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    public static RewriteFunction<String, Map> returnCodeMappingFunction() {
        return (exchange, s) -> {
            try {
                return Mono.just(returnCodeMapping(s));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        };
    }
}
