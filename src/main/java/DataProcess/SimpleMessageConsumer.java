package DataProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;



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

 private String receiveMessage() throws JMSException {
        TextMessage textMessage;
        if (destination == null) {
            textMessage = (TextMessage) jmsTemplate.receive();
            LOG.info("Receive" + textMessage);
        } else {

            textMessage = (TextMessage) jmsTemplate.receive(destination);
            LOG.info("Receive" + textMessage);
        }
        return textMessage.getText();
    }


    @Override
    public synchronized void start() {
        try {
            while (true){
            receiveMessage();

            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

