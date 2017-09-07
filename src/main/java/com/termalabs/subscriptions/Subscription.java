package com.termalabs.subscriptions;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Subscription {
	
	public enum SubscriptionType {
		PREDICTIONS("predictions"), ALERTS("alerts"), EVENTS("events");
		
		private String value;

		private SubscriptionType(String value) {
			this.value = value;
		}  
		
	    public String getValue() {
	        return this.value;
	    }
		private static final Map<String, SubscriptionType> reverse = new HashMap<>();

	    static {
	        for (SubscriptionType subsscriptionType : SubscriptionType.values()) {
	            reverse.put(subsscriptionType.getValue(), subsscriptionType);
	        }
	    }
	    public static SubscriptionType get(String value) {
	        return reverse.get(value);
	    }


		
		
	}
	
	private String url;
	private SubscriptionType type;
	
	public Subscription() {
		
	}

	public Subscription(String url, String type) {
		this.url = url;
		this.type = SubscriptionType.get(type);
	}

	public String getUrl() {
		return url;
	}

	public SubscriptionType getType() {
		return type;
	}
	
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
