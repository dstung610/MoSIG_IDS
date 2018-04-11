
// package Game;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.io.UnsupportedEncodingException;

public class Sender {

	private String m_sQueueName;
	private String m_sHost;
	private Channel channel;
	private Connection connection;
	private ConnectionFactory factory;

	public Sender(String host, String queueName) {
		try {

			m_sQueueName = queueName;
			m_sHost = host;
			factory = new ConnectionFactory();
			factory.setHost(m_sHost);
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(m_sQueueName, false, false, false, null);
		} catch (IOException ie) {
			System.out.println(ie.getMessage());
		} catch (TimeoutException te) {
			System.out.println(te.getMessage());
		}
	}

	public void close() {
		try {
			channel.close();
			connection.close();
		} catch (IOException ie) {
			System.out.println(ie.getMessage());
		} catch (TimeoutException te) {
			System.out.println(te.getMessage());
		}
	}

	public void send(String message) {
		try {
			// String message = "Hello World!";
			channel.basicPublish("", m_sQueueName, null, message.getBytes("UTF-8"));
			System.out.println(m_sQueueName + " [x] Sent '" + message + "'");
		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee.getMessage());
		} catch (IOException ie) {
			System.out.println(ie.getMessage());
		}
	}
}