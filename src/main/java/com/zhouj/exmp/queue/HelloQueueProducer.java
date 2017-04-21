package com.zhouj.exmp.queue;

import com.zhouj.exmp.common.LocationConfig;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by zhouj on 17/4/20.
 */
public class HelloQueueProducer {

    public static void main(String[] args) {
        // 生产者的主要流程
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
            // 4.创建Session，(是否支持事务)
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 5.创建消息目标
            Destination destination = session.createQueue("queue_lesson");
            //6.创建生产者
            MessageProducer producer = session.createProducer(destination);
            //7.配置消息是否持久化
            /*  DeliverMode有2种方式：
             *
                public interface DeliveryMode {
                    static final int NON_PERSISTENT = 1;//不持久化：服务器重启之后，消息销毁

                    static final int PERSISTENT = 2;//持久化：服务器重启之后，该消息仍存在
                }
             */
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            //8.初始化要发送的消息
            TextMessage message = session.createTextMessage("Hello World ! from yuguiyang");
            //9.发送消息
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally{
            try {
                //10.关闭连接
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
