package com.termalabs.subscriptions;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SubscriptionContent {
	
	private final ObjectMapper om = new ObjectMapper();
	
	public String asJson() throws Exception {
		return om.writeValueAsString(this);
	}

}
