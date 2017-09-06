package rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SubscriptionListener {

  private final static String SUBSCRIPTION_QUEUE = "subscriptionQueue";
  
  private Connection connection;
  
  public SubscriptionListener() throws IOException, TimeoutException {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    this.connection = factory.newConnection();
  }

  public static void main(String[] argv) throws Exception {
	SubscriptionListener subscriptionListener = new SubscriptionListener();
	subscriptionListener.listen();

  }

	private void listen() throws Exception {
	    Channel subscriptionChannel = connection.createChannel();

	    subscriptionChannel.queueDeclare(SUBSCRIPTION_QUEUE, true, false, false, null);

	    Consumer subscriptionConsumer = new DefaultConsumer(subscriptionChannel) {
	      @Override
	      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
	          throws IOException {
	        String message = new String(body, "UTF-8");
	        System.out.println(" [x] Received '" + message + "'");
	      }
	    };
	    subscriptionChannel.basicConsume(SUBSCRIPTION_QUEUE, true, subscriptionConsumer);

	}
}