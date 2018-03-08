import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class RingRMQ {

    private static final String TASK_QUEUE_NAME = "THE_RING";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

        String message = getMessage(argv);

        channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();

        int size = 3;
        NodeRMQ rings[] = new NodeRMQ[size];
        for (int i = 0; i < size; i++)
            rings[i] = new NodeRMQ(i);

        for (int i = 0; i < size; i++)
            rings[i].SetNeighbors(rings[(i + size - 1) % size], rings[(i + size + 1) % size]);

    }

    class NodeRMQ implements Runnable {
        Channel nextChannel;
        int id;
        NodeRMQ(int id) {
            this.id = id;

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            final Connection connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            channel.basicQos(2);

            final Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                        byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");

                    System.out.println(" [x] Received '" + message + "'");
                    try {
                        doWork(message);
                    } finally {
                        System.out.println(" [x] Done");
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };
            channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
        }

        private void doWork(String task) {
            System.out.println("["+ this.id +"] received " + task);
            String[] messageArray = task.split(",");
            if (id == messageArray[0]) {
                System.out.println(" [x] Tour done ':" + message + "'");
            } else {
                System.out.println("["+ this.id +"] sent " + task);
                nextChannel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
                    task.getBytes("UTF-8"));
            }
        }

        void SetNeighbors(NodeRMQ left, NodeRMQ right) {
            this.left = left;
            this.right = right;
        }

        public void run() {
            // compute primes larger than minPrime
            //  . . .
        }
    }
}
