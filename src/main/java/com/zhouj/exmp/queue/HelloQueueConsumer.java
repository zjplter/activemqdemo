package com.zhouj.exmp.queue;

import com.zhouj.exmp.common.LocationConfig;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by zhouj on 17/4/20.
 */
public class HelloQueueConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        //如果消息是TextMessage
        if (message instanceof TextMessage) {
            //强制转换一下
            TextMessage txtMsg = (TextMessage) message;
            try {
                //输出接收到的消息
                System.out.println("HaHa: I'v got " + txtMsg.getText());
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
            Destination destination = session.createQueue("queue_lesson");
            //6.创建消费者
            MessageConsumer consumer = session.createConsumer(destination);
            //7.配置监听
            consumer.setMessageListener(this);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HelloQueueConsumer helloQueueConsumer = new HelloQueueConsumer();
        helloQueueConsumer.receive();
    }

}
