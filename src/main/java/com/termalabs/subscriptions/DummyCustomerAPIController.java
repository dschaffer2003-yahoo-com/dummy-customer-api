package com.termalabs.subscriptions;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping(path = "/dummy-customer-api")
public class DummyCustomerAPIController {

	private static int msgCount = 0;

	@PutMapping(
			value = "alert",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void alert(@RequestBody String alert) throws Exception {
		System.out.println(String.format("Received alert %s", alert));
		delayIfConfigured("ALERT_DELAY_IN_SECONDS");
	}

	@PutMapping(
			value = "prediction/{jobStreamId}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void predictionViaPut(
			@PathVariable String jobStreamId,
			@RequestBody String prediction) throws Exception {
		System.out.println(String.format("For jobStreamId %s, Received prediction %s via PUT", 
				jobStreamId, prediction));
		delayIfConfigured("PREDICTION_DELAY_IN_SECONDS");
	}
	
	@PostMapping(
			value = "prediction/{jobStreamId}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void predictionViaPost(
			@PathVariable String jobStreamId,
			@RequestBody String prediction) throws Exception {
		System.out.println(String.format("For jobStreamId %s, Received prediction %s via POST", 
				jobStreamId, prediction));
		delayIfConfigured("PREDICTION_DELAY_IN_SECONDS");
	}

	@PutMapping(
			value = "schedulerData",
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public void schedulerData(@RequestBody String schedulerData) throws Exception {
		System.out.println(String.format("Received scheduler data for msg %d", Integer.valueOf(msgCount)));
		msgCount = msgCount + 1;
		delayIfConfigured("SCHEDULER_DELAY_IN_SECONDS");
	}

	@PutMapping(
			value = "event",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void event(@RequestBody String event) throws Exception {
		System.out.println(String.format("Received event %s", event));
		delayIfConfigured("EVENT_DELAY_IN_SECONDS");
	}
	
	@PutMapping(
			value = "jobStreamRunTransitioned",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void jobStreamRunTransitioned(@RequestBody String jobStreamRunTransitioned) throws Exception {
		System.out.println(String.format("Received jobStreamRunTransitioned %s", jobStreamRunTransitioned));
		delayIfConfigured("JOB_STREAM_RUN_TRANSITIONED_DELAY_IN_SECONDS");
	}
	
	@PutMapping(
			value = "general/{topicName}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void general(
			@PathVariable String topicName, 
			@RequestBody String payload) throws Exception {
		System.out.println(String.format("Received payload %s for topic %s", payload, topicName));
	}

	private void delayIfConfigured(String delayEnvVariable) throws InterruptedException {
		String delayAsString = System.getenv(delayEnvVariable);
		if (delayAsString != null) {
			System.out.println(String.format("Delaying for %s seconds.", delayAsString));
            Thread.sleep(Integer.valueOf(delayAsString).intValue()*1000);
		}
	}

}
