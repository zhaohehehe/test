package zhaohe.study.activeMq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class TopicProducer {
	@Test
	public void testTopicProducer() throws Exception {
		// 第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
		// brokerURL服务器的ip及端口号
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://133.160.136.221:61616");
		// 第二步：使用ConnectionFactory对象创建一个Connection对象。
		Connection connection = connectionFactory.createConnection();
		// 第三步：开启连接，调用Connection对象的start方法。
		connection.start();
		// 第四步：使用Connection对象创建一个Session对象。
		// 第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
		// 第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个topic对象。
		// 参数：话题的名称。
		Topic topic = session.createTopic("test-topic");
		// 第六步：使用Session对象创建一个Producer对象。
		MessageProducer producer = session.createProducer(topic);
		// 第七步：创建一个Message对象，创建一个TextMessage对象。
		/*
		 * TextMessage message = new ActiveMQTextMessage(); message.setText(
		 * "hello activeMq,this is my first test.");
		 */
		TextMessage textMessage = session.createTextMessage("hello activeMq,this is my topic test");
		// 第八步：使用Producer对象发送消息。
		producer.send(textMessage);
		// 第九步：关闭资源。
		producer.close();
		session.close();
		connection.close();
	}
}
