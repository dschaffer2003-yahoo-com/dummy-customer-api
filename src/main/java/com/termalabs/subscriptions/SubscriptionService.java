package com.termalabs.subscriptions;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import com.termalabs.subscriptions.Subscription.SubscriptionType;

@Component
public class SubscriptionService {
	
	final static String PREDICTION_SUBSCRIPTION_QUEUE = "predictionSubscriptionQueue";

	private final ObjectMapper om;
	private final Connection connection;
	private final Map<SubscriptionType, Set<URL>> subscriptions;
	private Channel subscriptionChannel;

	public SubscriptionService() throws Exception {

		this.om = new ObjectMapper();
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		this.connection = factory.newConnection();
		this.subscriptions = new HashMap<>();
		Arrays.asList(SubscriptionType.values()).forEach(e -> subscriptions.put(e, new HashSet<>()));
		listenForPredictions();

	}
	
	public void addSubscription(String url, String type) throws Exception {
		Subscription subscription = new Subscription(url, type);
		System.out.println(" Adding subscription " + subscription);
		subscriptions.get(subscription.getType()).add(new URL("https://" + subscription.getUrl()));

	}

	public void deleteSubscription(String url, String type) throws Exception {
		Subscription subscription = new Subscription(url, type);
		System.out.println(" Deleting subscription " + subscription);
		subscriptions.get(subscription.getType()).remove(new URL("https://" + subscription.getUrl()));
		
	}

    @PreDestroy
    public void close() throws Exception {
		subscriptionChannel.close();
		connection.close();
    }

	private void listenForPredictions() throws Exception {
		this.subscriptionChannel = connection.createChannel();

		subscriptionChannel.queueDeclare(PREDICTION_SUBSCRIPTION_QUEUE, true, false, false, null);

		Consumer subscriptionConsumer = new DefaultConsumer(subscriptionChannel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				Prediction prediction = om.readValue(body, Prediction.class);
				subscriptions.get(Subscription.SubscriptionType.PREDICTION)
				    .forEach(url->System.out.println("Sending prediction to URL " + prediction +  " " + url));
			}
		};
		subscriptionChannel.basicConsume(PREDICTION_SUBSCRIPTION_QUEUE, true, subscriptionConsumer);
		
	}

}
