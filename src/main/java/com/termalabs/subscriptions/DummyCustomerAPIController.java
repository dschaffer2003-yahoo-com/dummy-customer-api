package com.termalabs.subscriptions;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping(path = "/dummy-customer-api")
public class DummyCustomerAPIController {

	private static Integer msgCount = 0;

	@RequestMapping(
			method = RequestMethod.PUT, value = "alert",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void alert(@RequestBody String alert) throws Exception {
		System.out.println(String.format("Received alert %s", alert));
	}

	@RequestMapping(
			method = RequestMethod.PUT, value = "prediction",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void prediction(@RequestBody String prediction) throws Exception {
		System.out.println(String.format("Received prediction %s", prediction));
	}

	@RequestMapping(
			method = RequestMethod.PUT, value = "schedulerData",
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public void schedulerData(@RequestBody String schedulerData) throws Exception {
		synchronized (msgCount) {
			System.out.println(String.format("Received scheduler data for msg %d", msgCount));
			msgCount += 1;

		}
	}

	@RequestMapping(
			method = RequestMethod.PUT, value = "event",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void event(@RequestBody String event) throws Exception {
		System.out.println(String.format("Received event %s", event));
	}

}
