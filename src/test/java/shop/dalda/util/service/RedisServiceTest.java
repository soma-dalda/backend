package shop.dalda.util.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
class RedisServiceTest {

    @Autowired RedisService redisService;

    @Test
    @DisplayName("redis main set/get test")
    void redis_main_set_get_test() {
        //given
        String key = "key";
        String value = "value";

        redisService.setValues(key, value);
        //when
        String getValue = redisService.getValues(key);
        //then
        assertThat(value).isEqualTo(getValue);
        redisService.deleteValues(key);
    }
}