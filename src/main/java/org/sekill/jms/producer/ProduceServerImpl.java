package org.sekill.jms.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class ProduceServerImpl implements ProduceService{

    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource(name = "topicDestination")
    Destination destination;

    public void sendMessage(final String message) {
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                System.out.println("send info : " + message);
                return session.createTextMessage(message);
            }
        });
    }
}
