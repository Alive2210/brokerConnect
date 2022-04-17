package DataProcess;

import org.apache.activemq.jms.pool.PooledConnectionFactory;

import javax.jms.*;

public class Producer extends Thread {

    public PooledConnectionFactory getPooledConnectionFactory() {
        return pooledConnectionFactory;
    }

    public void setPooledConnectionFactory(PooledConnectionFactory pooledConnectionFactory) {
        this.pooledConnectionFactory = pooledConnectionFactory;
    }

    PooledConnectionFactory pooledConnectionFactory;

    public void sendMessage(MyObject myObject) {
        Connection connection = null;
        Session session = null;
        try {
            connection = pooledConnectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue asda = session.createQueue("asda");
            MessageProducer producer = session.createProducer(asda);
            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(myObject);
            producer.send(objectMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                assert session != null;
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public synchronized void start() {
        MyObject myObject = new MyObject();
        myObject.setAge(10);
        myObject.setName("name");
        sendMessage(myObject);
    }
}
