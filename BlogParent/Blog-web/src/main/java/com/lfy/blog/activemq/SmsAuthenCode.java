package com.lfy.blog.activemq;

import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * ActiveMQ 消息监听器的创建（消费者）
 */
@Component
public class SmsAuthenCode implements MessageListener {
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        // 调用SMS服务发送短信   SmsSystem阿里大于发送短信给客户手机实现类

        try {
            // 大于发送短信 Map 来自ActiveMQ 生成者
            SendMessage.sendMessages( mapMessage.getString("code"), mapMessage.getString("telephone") );
            System.out.println( "-----发送消息成功..."+mapMessage.getString("code"));
        } catch (Exception e) {//JMS
            e.printStackTrace();
        }
    }
}
