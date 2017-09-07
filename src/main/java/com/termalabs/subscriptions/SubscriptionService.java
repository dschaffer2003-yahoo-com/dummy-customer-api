package com.termalabs.subscriptions;

import static com.termalabs.shared.Constants.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

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


	private final Connection connection;
	private final Map<SubscriptionType, Set<URL>> subscriptions;
	private Channel subscriptionChannel;

	public SubscriptionService() throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		this.connection = factory.newConnection();
		this.subscriptions = new HashMap<>();
		Arrays.asList(SubscriptionType.values()).forEach(e -> subscriptions.put(e, new HashSet<>()));
		this.subscriptionChannel = connection.createChannel();
		listenForPredictions();
		listenForAlerts();
		listenForEvents();
	}
	
	public void addSubscription(String url, String type) throws Exception {
		Subscription subscription = new Subscription(url, type);
		System.out.println("Adding subscription " + subscription);
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

		subscriptionChannel.queueDeclare(PREDICTIONS_SUBSCRIPTION_QUEUE, true, false, false, null);

		Consumer subscriptionConsumer = new DefaultConsumer(subscriptionChannel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String asString = new String(body);
				subscriptions.get(Subscription.SubscriptionType.PREDICTIONS)
				    .forEach(url->System.out.println(String.format("Sending prediction %s to URL %s ", asString, url)));
			}
		};
		subscriptionChannel.basicConsume(PREDICTIONS_SUBSCRIPTION_QUEUE, true, subscriptionConsumer);
		
	}
	
	private void listenForAlerts() throws Exception {

		subscriptionChannel.queueDeclare(ALERTS_SUBSCRIPTION_QUEUE, true, false, false, null);

		Consumer subscriptionConsumer = new DefaultConsumer(subscriptionChannel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String asString = new String(body);
				subscriptions.get(Subscription.SubscriptionType.ALERTS)
				    .forEach(url->System.out.println(String.format("Sending alert %s to URL %s ", asString, url)));
			}
		};
		subscriptionChannel.basicConsume(ALERTS_SUBSCRIPTION_QUEUE, true, subscriptionConsumer);
		
	}
	
	private void listenForEvents() throws Exception {

		subscriptionChannel.queueDeclare(EVENTS_SUBSCRIPTION_QUEUE, true, false, false, null);

		Consumer subscriptionConsumer = new DefaultConsumer(subscriptionChannel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String asString = new String(body);
				subscriptions.get(Subscription.SubscriptionType.EVENTS)
				    .forEach(url->System.out.println(String.format("Sending event %s to URL %s ", asString, url)));
			}
		};
		subscriptionChannel.basicConsume(EVENTS_SUBSCRIPTION_QUEUE, true, subscriptionConsumer);
		
	}

}
