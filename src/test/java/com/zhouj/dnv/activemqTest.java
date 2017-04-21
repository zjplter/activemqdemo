package com.zhouj.dnv;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhouj on 17/4/20.
 */
public class activemqTest {

    @Test
    public void TestMain() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("activemq.xml");

        SpringProducer producer = (SpringProducer)applicationContext.getBean("springProducer");
        producer.send();

        SpringConsumer consumer = (SpringConsumer) applicationContext.getBean("springConsumer");
        consumer.recive();
    }
}
