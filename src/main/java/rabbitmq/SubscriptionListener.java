package rabbitmq;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import rabbitmq.Subscription.SubscriptionType;

public class SubscriptionListener {

	private final static String ADD_SUBSCRIPTION_QUEUE = "addSubscriptionQueue";
	private final Map<URL, SubscriptionType> subscriptions;
	private final ObjectMapper om;

	private Connection connection;

	public SubscriptionListener() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		this.connection = factory.newConnection();
		this.subscriptions = new HashMap<>();
		this.om = new ObjectMapper();
	}

	public static void main(String[] argv) throws Exception {
		SubscriptionListener subscriptionListener = new SubscriptionListener();
		subscriptionListener.listenForAdds();

	}

	private void listenForAdds() throws Exception {
		Channel subscriptionChannel = connection.createChannel();

		subscriptionChannel.queueDeclare(ADD_SUBSCRIPTION_QUEUE, true, false, false, null);

		Consumer subscriptionConsumer = new DefaultConsumer(subscriptionChannel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				Subscription subscription = om.readValue(body, Subscription.class);
				subscriptions.put(new URL(subscription.getUrl()), subscription.getSubscriptionType());
				System.out.println(" Adding subscription " + subscription);
			}
		};
		subscriptionChannel.basicConsume(ADD_SUBSCRIPTION_QUEUE, true, subscriptionConsumer);

	}
}