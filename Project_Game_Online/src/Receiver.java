
// package Game;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.LinkedList;

public class Receiver {

	private String m_sQueueName;
	private String m_sHost;
	private Channel channel;
	private Connection connection;
	private ConnectionFactory factory;

	public Receiver(String host, String queueName, LinkedList<String> sBuffer) {
		m_sQueueName = queueName;
		m_sHost = host;		

		try {
			factory = new ConnectionFactory();
			factory.setHost(m_sHost);

			connection = factory.newConnection();

			channel = connection.createChannel();
			channel.queueDeclare(m_sQueueName, false, false, false, null);

			// System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
						throws IOException {
					String message = new String(body, "UTF-8");
					System.out.println(queueName + " [x] Received '" + message + "'");
					sBuffer.add(message);
				}
			};
			channel.basicConsume(m_sQueueName, true, consumer);
		} catch (IOException ie) {
			System.out.println(ie.getMessage());
		} catch (TimeoutException te) {
			System.out.println(te.getMessage());
		}

	}

	public void close() {

	}
}
