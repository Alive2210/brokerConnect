package DataProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.Serializable;


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

    public void sendMessage(final MyObject msg) {
        LOG.info("Producer sends " + msg);

        if (destination == null) {
            jmsTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    ObjectMessage objectMessage = session.createObjectMessage();
                    objectMessage.setObject(msg);
                    return objectMessage;
                }
            });
        } else {
            jmsTemplate.send(destination, new MessageCreator() {
                public Message createMessage(Session session)
                        throws JMSException {
                    ObjectMessage objectMessage = session.createObjectMessage();
                    objectMessage.setObject(msg);
                    return objectMessage;
                }
            });
        }
    }

    @Override
    public synchronized void start() {
        MyObject myObject = new MyObject();
        myObject.setAge(10);
        myObject.setName("name");
        for (int i = 0; i < 10; i++) {
            sendMessage(myObject);
        }


    }
}



