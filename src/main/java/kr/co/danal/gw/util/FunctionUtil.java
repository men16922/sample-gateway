package kr.co.danal.gw.util;

import kr.co.danal.gw.dto.AuthDto;
import kr.co.danal.gw.dto.ipn.PDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import reactor.core.publisher.Mono;

import java.util.Map;

import static kr.co.danal.gw.util.FilterUtil.*;

@Slf4j
public class FunctionUtil {

    private FunctionUtil(){}

    public static RewriteFunction<AuthDto, PDto> authMappingFunction() {
        return (exchange, s) -> {
            try {
                return Mono.just(authMapping(s));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    public static RewriteFunction<PDto, String> maptoStringFunction() {
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
