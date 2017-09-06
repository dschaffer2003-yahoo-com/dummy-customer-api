package rabbitmq;

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
	

}
