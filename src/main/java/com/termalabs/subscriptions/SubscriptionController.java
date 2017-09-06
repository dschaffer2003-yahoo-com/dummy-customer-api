package com.termalabs.subscriptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO Add clean exception handling
 * @author dschaffer
 *
 */
@RestController
@RequestMapping(path = "/jaws/subscription")
public class SubscriptionController {
	
	@Autowired
	private SubscriptionService subscriptionService;

	@RequestMapping(
			method = RequestMethod.POST, 
			value = "{subscriberUrl}/{subscriptionType}")
	public void addSubscription(@PathVariable String subscriberUrl, @PathVariable String subscriptionType) throws Exception {
		subscriptionService.addSubscription(subscriberUrl, subscriptionType);
	}
	
	@RequestMapping(
			method = RequestMethod.DELETE, 
			value = "{subscriberUrl}/{subscriptionType}")
	public void deleteSubscription(@PathVariable String subscriberUrl, @PathVariable String subscriptionType) throws Exception {
		subscriptionService.deleteSubscription(subscriberUrl, subscriptionType);
	}

}
