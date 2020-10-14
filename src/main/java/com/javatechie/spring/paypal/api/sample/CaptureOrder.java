package com.javatechie.spring.paypal.api.sample;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.OrderRequest;
import com.paypal.payments.AuthorizationsCaptureRequest;
import com.paypal.payments.Capture;
import com.paypal.payments.LinkDescription;

public class CaptureOrder extends PayPalClient {

	/**
	 * Creating empty body for capture request
	 *
	 * @return OrderRequest request with empty body
	 */
	public OrderRequest buildRequestBody() {
		return new OrderRequest();
	}

	/**
	 * Method to capture order after authorization
	 *
	 * @param authId Authorization ID from authorizeOrder response
	 * @param debug  true = print response data
	 * @return HttpResponse<Capture> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	public HttpResponse<Capture> captureOrder(String authId, boolean debug) throws IOException, JSONException {
		AuthorizationsCaptureRequest request = new AuthorizationsCaptureRequest(authId);
		request.requestBody(buildRequestBody());
		HttpResponse<Capture> response = client().execute(request);
		if (debug) {
			System.out.println("Status Code: " + response.statusCode());
			System.out.println("Status: " + response.result().status());
			System.out.println("Capture ID: " + response.result().id());
			System.out.println("Links: ");
			for (LinkDescription link : response.result().links()) {
				System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
			}
			System.out.println("Full response body:");
			System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
		}
		return response;
	}

	/**
	 * This is the driver function which invokes the captureOrder function to
	 * perform capture on authorization. Authorization Id should be replaced with
	 * the valid authorization id.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new CaptureOrder().captureOrder("<<REPLACE-WITH-VALID-AUTHORIZATION-ID>>", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
