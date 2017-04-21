package com.zhouj.exmp.topic;

import com.zhouj.exmp.common.LocationConfig;
import com.zhouj.exmp.common.MQConstants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


/**
 * Created by zhouj on 17/4/20.
 */
public class HelloTopicProducer {

    public void send(String msg) {

        Connection connection = null;

        try {
            // 1.初始化connection工厂
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    LocationConfig.DEFAULT_USER,
                    LocationConfig.DEFAULT_PASSWORD,
                    LocationConfig.BROKERURL);
            // 2.创建Connection
            connection = connectionFactory.createConnection();
            // 3.打开连接
            connection.start();
            // 4.创建session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 5.创建消息目标
            Destination destination_send = session.createTopic(MQConstants.DESTINATION_SEND);
            // 6.创建生产者
            MessageProducer producer = session.createProducer(destination_send);
            // 7.配置消息是否持久化
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // 8.初始化要发送的消息
            TextMessage message = session.createTextMessage(msg);
            // 9.发送消息
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        HelloTopicProducer helloTopicProducer = new HelloTopicProducer();
        helloTopicProducer.send("我来试一试发布/订阅...");
    }
}
