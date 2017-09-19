package com.termalabs.subscriptions;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dschaffer
 *
 */
@RestController
@RequestMapping(path = "/dummy-customer-api")
public class DummyCustomerAPIController {

	@RequestMapping(
			method = RequestMethod.PUT, 
			value = "alert/early")
	public void earlyAlert(@RequestBody String alert) throws Exception {
		System.out.println(String.format("Received alert %s", alert));
	}
	

}
