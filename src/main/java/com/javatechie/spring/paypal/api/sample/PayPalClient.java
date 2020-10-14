package com.javatechie.spring.paypal.api.sample;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.Iterator;

public class PayPalClient {

	/**
	 * Setting up PayPal SDK environment with PayPal Access credentials. For demo
	 * purpose, we are using SandboxEnvironment. In production this will be
	 * LiveEnvironment.
	 */
	@Value("${paypal.client.id}")
	 String clientId;
	@Value("${paypal.client.secret}")
	 String secret;

	private PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
			System.getProperty("PAYPAL_CLIENT_ID") != null ? System.getProperty("PAYPAL_CLIENT_ID")
					: "Aee3S7IxGuC3HrmlgCZ9pzWOFmee9eBbOddIO3aDGT1P5o7-WvR9R2yKSMmTPZPRjs0MfHCxvxikLeNJ",
			System.getProperty("PAYPAL_CLIENT_SECRET") != null ? System.getProperty("PAYPAL_CLIENT_SECRET")
					: "EDYgf5k1WnqpzxEnybbEKeVhAbk4yKL-kTBPWxG6L-WpCI56HQpKVhJysC9yBXg6fW3yRk4p9VGAV4fH");

	/**
	 * PayPal HTTP client instance with environment which has access credentials
	 * context. This can be used invoke PayPal API's provided the credentials have
	 * the access to do so.
	 */
	PayPalHttpClient client = new PayPalHttpClient(environment);

	/**
	 * Method to get client object
	 *
	 * @return PayPalHttpClient client
	 */
	public PayPalHttpClient client() {
		return this.client;
	}


}
