package com.yue.controller;


import com.yue.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queuesToDeclare = @Queue("send_email"))
public class EmailSender {

    @Autowired
    JavaMailSender javaMailSender;

    @RabbitHandler
    public void receive1(User user){
        log.info("用户"+user.getNickname()+"注册成功，发送邮件提醒，用户邮箱："+user.getEmail());
        //发送邮件通知用户注册成功
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setText("用户"+user.getNickname()+" PartJob Platform 注册成功！localhost:8080,如非本人操作，请忽略本邮件。");
        mailMessage.setSubject("PartJob Platform 注册成功！");
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("17635013670@163.com");
        //mailMessage.setCc("抄送人");
        //mailMessage.setBcc("密送人");
        javaMailSender.send(mailMessage);
    }
}
