package com.zhouj.dnv;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by zhouj on 17/4/20.
 */
public class SpringProducer {
    //Spring的模板，封装了很多功能
    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    public void send() {
        //使用JMSTemplate可以很简单的实现发送消息
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("地瓜！地瓜！");
            }
        });
    }
}
