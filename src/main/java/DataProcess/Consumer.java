package DataProcess;

import org.apache.activemq.jms.pool.PooledConnectionFactory;

import javax.jms.*;

public class Consumer extends Thread {

    public PooledConnectionFactory getPooledConnectionFactory() {
        return pooledConnectionFactory;
    }

    public void setPooledConnectionFactory(PooledConnectionFactory pooledConnectionFactory) {
        this.pooledConnectionFactory = pooledConnectionFactory;
    }

    PooledConnectionFactory pooledConnectionFactory;


    public void getMessage() {
        Connection connection = null;
        Session session = null;
        MyObject object = null;
        ObjectMessage objectMessage = null;
        try {
            connection = pooledConnectionFactory.createConnection();

            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue asda = session.createQueue("asda");
            MessageConsumer consumer = session.createConsumer(asda);
            objectMessage = (ObjectMessage) consumer.receive();
            System.out.println(objectMessage.getObject());

        } catch (JMSException e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert session != null;
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        try {
            assert objectMessage != null;
            object = (MyObject) objectMessage.getObject();
            System.out.println(object.getAge());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void start() {
//        getMessage();
    }
}
