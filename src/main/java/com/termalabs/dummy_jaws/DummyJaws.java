package com.termalabs.dummy_jaws;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import com.termalabs.subscriptions.Prediction;

public class DummyJaws {
	final static String PREDICTION_SUBSCRIPTION_QUEUE = "predictionSubscriptionQueue";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection()) {

			Channel channel = connection.createChannel();

			AMQP.BasicProperties queueProperties = new AMQP.BasicProperties.Builder().contentType("text/plain")
					.deliveryMode(2).build();

			channel.queueDeclare(PREDICTION_SUBSCRIPTION_QUEUE, true, false, false, null);
			String message = new Prediction("some prediction").asJson();
			channel.basicPublish("", PREDICTION_SUBSCRIPTION_QUEUE, queueProperties, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");

			channel.close();
		}

	}

}
