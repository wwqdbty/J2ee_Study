package com.kulang.activeMq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

/**
 * ActiveMq 发布/订阅 样例
 *
 * Created by wenqiang.wang on 2016/5/30.
 */
public class PublishAndSubscribe {

}

/**
 * ActiveMq 发布/订阅 服务端
 */
class TopicRequest {
    // JMS连接
    private Connection connection = null;

    // JMS客户-生产者(消息发布者)
    private MessageProducer messageProducer;

    // JMS会话-JMS客户与JMS服务器之间的一个会话线程
    private Session session;

    public void init() throws Exception {
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
            // 指定JMS消息主题
            Topic topic = session.createTopic("MessageTopicTest1");

            // 创建JMS客户-生产者
            messageProducer = session.createProducer(topic);

            // 非持久连接
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void submitMessage(HashMap<Serializable, Serializable> requestParam) throws Exception {
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
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.init();

        // 消息发送
        for (int i = 0; i < 1000; i++) {
            HashMap<Serializable, Serializable> requestParam = new HashMap<Serializable, Serializable>();
            requestParam.put("PublishAndSubscribe-->王文强", "WWQ" + new Random().nextInt());

            topicRequest.submitMessage(requestParam);
        }

        topicRequest.close();
    }
}

/**
 * ActiveMq 发布/订阅 客户端
 */
class TopicReceive {
    // JMS连接
    private Connection connection = null;

    // JMS客户-消费者
    private MessageConsumer messageConsumer;

    // JMS会话
    private Session session;

    public void init() throws Exception {
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
            // 指定JMS消息主题
            Topic topic = session.createTopic("MessageTopicTest1");

            // 创建JMS客户-消费者(订阅者)
            messageConsumer = session.createConsumer(topic);

            /*messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    Serializable returnObject = null;
                    try {
                        returnObject = objectMessage.getObject();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                    System.out.println(returnObject);
                }
            });*/
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void receive(final IMessageProcessor messageProcessor) {
        try {
            messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    Serializable returnObject = null;
                    try {
                        returnObject = objectMessage.getObject();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                    // 回调处理
                    messageProcessor.messageProcess(returnObject);
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        TopicReceive topicReceive = new TopicReceive();
        topicReceive.init();
        topicReceive.receive(new IMessageProcessor() {
            @Override
            public void messageProcess(Serializable serializable) {
                System.out.println("PublishAndSubscribe-->messageProcess....." + serializable.toString());
            }
        });
    }
}