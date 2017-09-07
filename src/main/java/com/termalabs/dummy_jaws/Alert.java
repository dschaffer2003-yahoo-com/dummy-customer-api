package com.termalabs.dummy_jaws;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.termalabs.subscriptions.SubscriptionContent;

public class Alert extends SubscriptionContent {
	private String content;
	
	public Alert() {
		
	}

	public Alert(String content) {
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
