package com.xss.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/19 0019
 * rabbit 消息生产者
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate; //注入接口实现消息发送

    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }


}
