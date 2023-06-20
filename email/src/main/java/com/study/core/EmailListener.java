package com.study.core;

import com.study.common.EmailMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Component
public class EmailListener {
    @Value("${spring.mail.username}")
    private String from;
    @Resource
    private JavaMailSender javaMailSender;
    @RabbitListener(queues = "emailQueue")
    public void listenEmail(Message message) throws MessagingException {
        byte[] body = message.getBody();
        ByteArrayInputStream arrayInputStream = null;
        ObjectInputStream is = null;
        try {
            // 接收消息
            arrayInputStream = new ByteArrayInputStream(body);
            is = new ObjectInputStream(arrayInputStream);
            Object o = is.readObject();
            EmailMessage emailMessage = (EmailMessage) o;
            // 发送邮件
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(from);
            helper.setTo(emailMessage.getTo());
            helper.setSubject(emailMessage.getSubject());
            helper.setText(emailMessage.getText(), true);
            javaMailSender.send(mimeMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (arrayInputStream != null) {
                try {
                    arrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
