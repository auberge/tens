package com.wsit.tensquare.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "itcast")
public class Customer {

    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("直接模式消费"+msg);
    }

}
