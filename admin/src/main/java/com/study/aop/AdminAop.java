package com.study.aop;

import com.study.common.EmailMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Aspect
public class AdminAop {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @AfterReturning(value = "execution(public * com.study.service.impl.UmsUserServiceImpl.add(..))")
    public void handler(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        // 用户名
        String name = args[0].toString();
        // 邮箱
        String to = args[2].toString();
        // 手机号
        String phone = args[1].toString();
        StringBuilder builder = new StringBuilder();
        builder.append("<h2>尊敬的"+name+"</h2>")
                .append("<p>系统为您创建了系统账号，用户名和密码均为："+phone+"</p>")
                .append("<p>您可以登录系统使用</p>");
        EmailMessage emailMessage = new EmailMessage(
                "系统消息",
                to,
                builder.toString()
        );
        rabbitTemplate.convertAndSend("emailExchange", "emailKey", emailMessage);
    }
}
