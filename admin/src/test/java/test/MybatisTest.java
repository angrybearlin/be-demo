package test;

import com.study.AdminApp;
import com.study.entity.UmsUser;
import com.study.service.UmsUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApp.class)
public class MybatisTest {
    @Resource
    private UmsUserService umsUserService;
    @Test
    public void handler() {
        List<UmsUser> list = new ArrayList<>();
        UmsUser user = null;
        for (int i=0; i< 100; i++) {
            user = new UmsUser(
                    "zs" + i,
                    "137" + i,
                    i + "@qq.com",
                    i % 2,
                    "123456"
            );
            list.add(user);
        }
        umsUserService.saveBatch(list);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123456"));
    }
}
