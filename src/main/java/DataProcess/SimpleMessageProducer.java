package DataProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;


@Service
public class SimpleMessageProducer extends  Thread{
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

    public void sendMessage(final String msg) {
        LOG.info("Producer sends " + msg);
        if (destination == null) {
            jmsTemplate.send(new MessageCreator() {
                public Message createMessage(Session session)
                        throws JMSException {
                    return session.createTextMessage(msg);
                }
            });
        } else {
            jmsTemplate.send(destination, new MessageCreator() {
                public Message createMessage(Session session)
                        throws JMSException {
                    return session.createTextMessage(msg);
                }
            });
        }
    }

    @Override
    public synchronized void start() {
        for (int i = 0; i < 1000; i++) {
            sendMessage("hello");
        }


    }
}



