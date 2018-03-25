import DataProcess.SimpleMessageConsumer;
import DataProcess.SimpleMessageProducer;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.jms.JMSException;

import static java.lang.Thread.sleep;

class Main{
    private static final Logger LOG = LoggerFactory.getLogger(Math.class);

    public static void main(String[] args) throws JMSException, InterruptedException {

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        try {
            SimpleMessageProducer springJmsProducer = (SimpleMessageProducer) context
                    .getBean("springJmsProducer");

             //   springJmsProducer.start(); //sendMessage("SomeTask");



            SimpleMessageConsumer springJmsConsumer = (SimpleMessageConsumer) context
                    .getBean("springJmsConsumer");

         //  springJmsConsumer.start();

        } finally {
           /// context.close();
        }
    }
}