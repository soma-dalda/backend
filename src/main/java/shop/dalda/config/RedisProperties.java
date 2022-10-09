package shop.dalda.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "redis")
@Component
public class RedisProperties {

    private Main main = new Main();
    private List<Replica> replicas;

    @Data
    @RequiredArgsConstructor
    public static class Main {
        private String host;
        private int port;
    }

    @Data
    @RequiredArgsConstructor
    public static class Replica {
        private String host;
        private int port;
    }
}
