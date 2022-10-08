package shop.dalda.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "redis")
@Configuration
public class RedisProperties {
    private String host;
    private int port;
    private RedisProperties main;
    private List<RedisProperties> replicas;
}
