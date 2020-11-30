package com.wist.tensquare.rabbitsms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.wist.tensquare.rabbitsms.util.SmsUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    @Resource
    private SmsUtils smsUtils;
    @Value("${aliyun.sms.template_code}")
    private String template_code;
    @Value("${aliyun.sms.sign_name}")
    private String sign_name;

    @RabbitHandler
    public void executeSms(Map<String, String> map) {
        String mobile = map.get("mobile");
        String checkCode = map.get("checkCode");
        System.out.println(template_code+"   "+sign_name);
        try {
            smsUtils.sendSms(mobile, template_code, sign_name, "{\"checkCode\":\"" + checkCode + "\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
