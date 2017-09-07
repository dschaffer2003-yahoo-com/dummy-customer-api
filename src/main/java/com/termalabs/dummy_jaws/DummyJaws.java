package com.termalabs.dummy_jaws;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import static com.termalabs.shared.Constants.ALERTS_SUBSCRIPTION_QUEUE;
import static com.termalabs.shared.Constants.EVENTS_SUBSCRIPTION_QUEUE;
import static com.termalabs.shared.Constants.PREDICTIONS_SUBSCRIPTION_QUEUE;

public class DummyJaws {

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection()) {

			Channel channel = connection.createChannel();

			publishPrediction(channel);
			publishAlert(channel);
			publishEvent(channel);

			channel.close();
		}

	}

	private static void publishEvent(Channel channel) throws IOException, Exception, UnsupportedEncodingException {
		String message;
		channel.queueDeclare(EVENTS_SUBSCRIPTION_QUEUE, true, false, false, null);
		message = new Event("some event").asJson();
		channel.basicPublish("", EVENTS_SUBSCRIPTION_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes("UTF-8"));
		System.out.println("Sent '" + message + "'");
	}

	private static void publishAlert(Channel channel) throws IOException, Exception, UnsupportedEncodingException {
		String message;
		channel.queueDeclare(ALERTS_SUBSCRIPTION_QUEUE, true, false, false, null);
		message = new Alert("some alert").asJson();
		channel.basicPublish("", ALERTS_SUBSCRIPTION_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes("UTF-8"));
		System.out.println("Sent '" + message + "'");
	}

	private static void publishPrediction(Channel channel) throws IOException, Exception, UnsupportedEncodingException {
		channel.queueDeclare(PREDICTIONS_SUBSCRIPTION_QUEUE, true, false, false, null);
		String message = new Prediction("some prediction").asJson();
		channel.basicPublish("", PREDICTIONS_SUBSCRIPTION_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes("UTF-8"));
		System.out.println("Sent '" + message + "'");
	}

}
