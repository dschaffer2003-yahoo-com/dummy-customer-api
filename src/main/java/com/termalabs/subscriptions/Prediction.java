package com.termalabs.subscriptions;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Prediction extends SubscriptionContent {
	private String content;
	
	public Prediction() {
		
	}

	public Prediction(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
