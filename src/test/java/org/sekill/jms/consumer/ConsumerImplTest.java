package org.sekill.jms.consumer;

import org.sekill.jms.producer.ProduceService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerImplTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/spring-jms-consumer.xml");

    }

}