package kr.co.danal.gw.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix="routing")
@Getter
@Setter
@ToString
public class RoutingProperties {

    private Ipn ipn;

    @Setter
    @Getter
    @ToString
    public static class Ipn {
        private String id;
        private String uri;
        private String path;
        private String method;
    }
}
