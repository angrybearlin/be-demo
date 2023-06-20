package test;

import com.study.AdminApp;
import com.study.common.EmailMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApp.class)
public class RabbitmqTest {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Test
    public void handler() {
        EmailMessage emailMessage = new EmailMessage(
                "邮件标题",
                "接收人",
                "内容"
        );
        rabbitTemplate.convertAndSend("emailExchange", "emailKey", emailMessage);
    }
}
