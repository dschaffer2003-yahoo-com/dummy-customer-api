package rabbitmq;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Subscription {
	
	public enum SubscriptionType {
		PREDICTION, ALERT, EVENT
	}
	
	private String url;
	private SubscriptionType subscriptionType;
	
	public Subscription() {
		
	}

	public String getUrl() {
		return url;
	}

	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}
	
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
