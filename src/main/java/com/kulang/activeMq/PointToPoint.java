package com.kulang.activeMq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * ActiveMq point-to-point 样例
 * Created by wenqiang.wang on 2016/5/30.
 */
public class PointToPoint {

}

/**
 * ActiveMq point-to-point 服务端
 * <p>
 * Created by wenqiang.wang on 2016/5/30.
 */
class RequestSubmit {
    // JMS连接
    private Connection connection = null;

    // JMS客户-生产者
    private MessageProducer messageProducer;

    // JMS会话-JMS客户与JMS服务器之间的一个会话线程
    private Session session;

    public void init() {
        // 连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://127.0.0.1:61616");

        try {
            // JMS连接
            connection = connectionFactory.createConnection();
            connection.start();

            // 创建JMS会话
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 指定JMS目标(队列)
            Destination destination = session.createQueue("requestQueue_Test1");

            // 创建JMS客户-生产者
            messageProducer = session.createProducer(destination);

            // 非持久连接
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void submitMessage(HashMap<Serializable, Serializable> requestParam) {
        ObjectMessage objectMessage = null;
        try {
            // 创建消息
            objectMessage = session.createObjectMessage(requestParam);
            messageProducer.send(objectMessage);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (messageProducer != null) {
                messageProducer.close();
            }
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        RequestSubmit requestSubmit = new RequestSubmit();
        requestSubmit.init();

        // 消息发送
        for (int i = 0; i < 1000; i++) {
            HashMap<Serializable, Serializable> requestParam = new HashMap<Serializable, Serializable>();
            requestParam.put("PointToPoint-->王文强", "WWQ" + new Random().nextInt());

            requestSubmit.submitMessage(requestParam);
        }

        requestSubmit.close();
    }
}

/**
 * ActiveMq point-to-point 客户端
 * <p>
 * Created by wenqiang.wang on 2016/5/30.
 */
class RequestProcessor {
    // JMS连接
    private Connection connection = null;

    // JMS客户-消费者
    private MessageConsumer messageConsumer;

    // JMS会话
    private Session session;

    public void init() {
        // 连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://127.0.0.1:61616");

        try {
            // JMS连接
            connection = connectionFactory.createConnection();
            connection.start();

            // 创建JMS会话
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 指定JMS目标(队列)
            Destination destination = session.createQueue("requestQueue_Test1");

            // 创建JMS客户-消费者
            messageConsumer = session.createConsumer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void receive(IMessageProcessor messageProcessor) {
        while (true) {
            ObjectMessage message = null;
            try {
                message = (ObjectMessage) messageConsumer.receive(1000);
            } catch (JMSException e) {
                e.printStackTrace();
            }

            if (null != message) {
                System.out.println(message);
                HashMap<Serializable, Serializable> requestParam = null;
                try {
                    requestParam = (HashMap<Serializable, Serializable>) message.getObject();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                // 回调进行处理
                messageProcessor.messageProcess(requestParam);
            }
        }
    }

    public void close() {
        try {
            if (messageConsumer != null) {
                messageConsumer.close();
            }
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        RequestProcessor requestProcessor = new RequestProcessor();
        requestProcessor.init();
        requestProcessor.receive(new IMessageProcessor() {
            @Override
            public void messageProcess(Serializable serializable) {
                System.out.println("PointToPoint-->messageProcess....." + serializable.toString());
                /*for (Map.Entry<Serializable, Serializable> entry : serializable.entrySet()) {
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                }*/
            }
        });

        /*// 关闭连接
        requestProcessor.close();*/
    }
}