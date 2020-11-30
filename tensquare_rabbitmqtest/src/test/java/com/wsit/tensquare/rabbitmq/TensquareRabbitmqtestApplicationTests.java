package com.wsit.tensquare.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitmqtestApplication.class)
public class TensquareRabbitmqtestApplicationTests {

//    @Test
//    public void contextLoads() {
//    }

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMsg(){
        rabbitTemplate.convertAndSend("itcast","直接模式测试");
    }

}
