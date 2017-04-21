package com.zhouj.exmp.topic;

import com.zhouj.exmp.common.LocationConfig;
import com.zhouj.exmp.common.MQConstants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by zhouj on 17/4/20.
 */
public class HelloTopicConsumer implements MessageListener {

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage txtMsg = (TextMessage) message;

            try {
                System.out.println("哈，我接收到了消息：" + txtMsg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public void receive() {
        // 消费者的主要流程
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
            Destination destination = session.createTopic(MQConstants.DESTINATION_SEND);
            // 6.创建消费者
            MessageConsumer consumer = session.createConsumer(destination);
            // 7.配置监听
            consumer.setMessageListener(new HelloTopicConsumer());

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HelloTopicConsumer helloTopicConsumer = new HelloTopicConsumer();
        helloTopicConsumer.receive();
    }
}
