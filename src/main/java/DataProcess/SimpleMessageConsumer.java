package DataProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.Serializable;


public class SimpleMessageConsumer extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleMessageConsumer.class);
    private JmsTemplate jmsTemplate;
    private Destination destination;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

 private ObjectMessage receiveMessage() throws JMSException {
     ObjectMessage receive;
        if (destination == null) {
            receive = (ObjectMessage) jmsTemplate.receive();
            LOG.info("Receive" + ((MyObject)receive.getObject()).getAge());
        } else {
            receive = (ObjectMessage) jmsTemplate.receive(destination);
            LOG.info("Receive" + ((MyObject)receive.getObject()).getName());
        }
     return receive;
    }


    @Override
    public synchronized void start() {
        try {
            while (true){
                MyObject object = (MyObject) receiveMessage().getObject();
                System.out.println(object.getAge());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

