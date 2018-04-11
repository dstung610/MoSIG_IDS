
// package Game;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.LinkedList;

public class ReceiverOpened {

	private String m_sQueueName;
	private String m_sExchangeName;
	private String m_sHost;
	private Channel channel;
	private Connection connection;
	private ConnectionFactory factory;

	public ReceiverOpened(String host, String sExchangeName, LinkedList<String> sBuffer) {
		
		m_sHost = host;		
		m_sExchangeName = sExchangeName;

		try {
			factory = new ConnectionFactory();
			factory.setHost(m_sHost);

			connection = factory.newConnection();

			channel = connection.createChannel();

			channel.exchangeDeclare(sExchangeName, "fanout");
			m_sQueueName = channel.queueDeclare().getQueue();
			channel.queueBind(m_sQueueName, sExchangeName, "");

			// System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
						throws IOException {
					String message = new String(body, "UTF-8");
					System.out.println(m_sQueueName + " [x] Received '" + message + "'");
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
