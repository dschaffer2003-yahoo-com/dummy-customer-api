package com.termalabs.subscriptions;

import java.io.IOException;

import javax.annotation.PreDestroy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import static com.termalabs.subscriptions.SubscriptionConstants.ADD_SUBSCRIPTION_QUEUE;
import static com.termalabs.subscriptions.SubscriptionConstants.DELETE_SUBSCRIPTION_QUEUE;

public class SubscriptionService {
	
	private final ObjectMapper om;
	private final Channel addChannel;
	private final Channel deleteChannel;
	private final BasicProperties queueProperties;
	private final Connection connection;

	public SubscriptionService() throws Exception {

		this.om = new ObjectMapper();
		queueProperties = new AMQP.BasicProperties.Builder()
				.contentType("text/plain")
				.deliveryMode(2)
				.build();
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		this.connection = factory.newConnection();
		
		addChannel = buildChannel(ADD_SUBSCRIPTION_QUEUE);
		deleteChannel = buildChannel(DELETE_SUBSCRIPTION_QUEUE);

	}
	
	public void addSubscription(String url, String type) throws Exception {
		Subscription subscription = new Subscription(url, type);
		String asJson = om.writeValueAsString(subscription);
		addChannel.basicPublish("", ADD_SUBSCRIPTION_QUEUE, queueProperties, asJson.getBytes("UTF-8"));

	}

	public void deleteSubscription(String url, String type) throws Exception {
		Subscription subscription = new Subscription(url, type);
		String asJson = om.writeValueAsString(subscription);
		deleteChannel.basicPublish("", DELETE_SUBSCRIPTION_QUEUE, queueProperties, asJson.getBytes("UTF-8"));
		
	}

    @PreDestroy
    public void close() throws Exception {
		addChannel.close();
		deleteChannel.close();
		connection.close();
    }
    
	private Channel buildChannel(String queueName) throws IOException {
		Channel result = connection.createChannel();
		result.queueDeclare(queueName, true, false, false, null);
		return result;
	}

	

}
