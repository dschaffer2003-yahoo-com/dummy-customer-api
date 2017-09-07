package com.termalabs.dummy_jaws;

import static com.termalabs.shared.Constants.*;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DummyJaws {

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection()) {

			Channel channel = connection.createChannel();

			AMQP.BasicProperties queueProperties = new AMQP.BasicProperties.Builder()
					.contentType("text/plain")
					.deliveryMode(2).build();

			channel.queueDeclare(PREDICTIONS_SUBSCRIPTION_QUEUE, true, false, false, null);
			String message = new Prediction("some prediction").asJson();
			channel.basicPublish("", PREDICTIONS_SUBSCRIPTION_QUEUE, queueProperties, message.getBytes("UTF-8"));
			System.out.println("Sent '" + message + "'");
			
			channel.queueDeclare(ALERTS_SUBSCRIPTION_QUEUE, true, false, false, null);
			message = new Alert("some alert").asJson();
			channel.basicPublish("", ALERTS_SUBSCRIPTION_QUEUE, queueProperties, message.getBytes("UTF-8"));
			System.out.println("Sent '" + message + "'");
			
			channel.queueDeclare(EVENTS_SUBSCRIPTION_QUEUE, true, false, false, null);
			message = new Event("some event").asJson();
			channel.basicPublish("", EVENTS_SUBSCRIPTION_QUEUE, queueProperties, message.getBytes("UTF-8"));
			System.out.println("Sent '" + message + "'");

			channel.close();
		}

	}

}
