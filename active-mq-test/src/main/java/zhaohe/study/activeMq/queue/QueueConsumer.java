package zhaohe.study.activeMq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class QueueConsumer {

	@Test
	public void testSynQueueConsumer() {
		// 创建一连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://133.160.136.221:61616");
		try {
			// 创建一个连接
			Connection connection = connectionFactory.createConnection();
			// 打开连接
			connection.start();
			// 创建一个回话
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// 创建一个目的地Destination
			Queue queue = session.createQueue("test-queue");
			// 创建一个消费者
			MessageConsumer consumer = session.createConsumer(queue);
			while (true) {
				// 设置接收者接收消息的时间，为了便于测试，这里定为100s
				Message message = consumer.receive(100000);
				if (message != null) {
					System.out.println(message);
				} else {
					// 超时结束
					break;
				}
			}
			consumer.close();
			session.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testAsynQueueConsumer() throws Exception {
		// 第一步：创建一个ConnectionFactory对象。
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://133.160.136.221:61616");
		// 第二步：从ConnectionFactory对象中获得一个Connection对象。
		Connection connection = connectionFactory.createConnection();
		// 第三步：开启连接。调用Connection对象的start方法。
		connection.start();
		// 第四步：使用Connection对象创建一个Session对象。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 第五步：使用Session对象创建一个Destination对象。和发送端保持一致queue，并且队列的名称一致。
		Queue queue = session.createQueue("test-queue");
		// 第六步：使用Session对象创建一个Consumer对象。
		MessageConsumer consumer = session.createConsumer(queue);
		// 第七步：接收消息。
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
					String text = null;
					// 取消息的内容
					text = textMessage.getText();
					// 第八步：打印消息。
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		// 等待键盘输入
		System.in.read();
		// 第九步：关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
}
