package test;

import com.study.AdminApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApp.class)
public class RedisTest {
    @Resource(name = "lkxRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void handler() {
        redisTemplate.opsForValue().set("a", "1234", 30, TimeUnit.SECONDS);
    }
}
