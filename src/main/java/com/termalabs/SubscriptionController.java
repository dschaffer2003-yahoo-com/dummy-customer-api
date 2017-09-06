package com.termalabs;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/jaws/subscription")
public class SubscriptionController {

	@RequestMapping(method = RequestMethod.POST, value = "{subscriberUrl}/{subscriptionType}")
	public void addSubscription(@PathVariable String subscriberUrl, @PathVariable String subscriptionType) {
	}

}
