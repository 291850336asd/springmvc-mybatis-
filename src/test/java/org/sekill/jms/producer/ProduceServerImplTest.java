package org.sekill.jms.producer;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class ProduceServerImplTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/spring-jms-produce.xml");
        ProduceService service = ac.getBean(ProduceService.class);
        for (int i = 1;i <=100; i++)
        {
            service.sendMessage("produce topic " + i);
        }
        ac.close();

    }

}