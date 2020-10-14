package com.javatechie.spring.paypal.api.sample;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.payments.CapturesRefundRequest;
import com.paypal.payments.LinkDescription;
import com.paypal.payments.Money;
import com.paypal.payments.Refund;
import com.paypal.payments.RefundRequest;

public class RefundOrder extends PayPalClient {

	/**
	 * Creating empty body for Refund request. This request body can be created with
	 * correct values as per the need.
	 *
	 * @return OrderRequest request with empty body
	 */
	public RefundRequest buildRequestBody() {
		RefundRequest refundRequest = new RefundRequest();
		Money money = new Money();
		money.currencyCode("USD");
		money.value("20.00");
		refundRequest.amount(money);
		return refundRequest;
	}

	/**
	 * Method to Refund the Capture. valid capture Id should be passed.
	 *
	 * @param captureId Capture ID from authorizeOrder response
	 * @param debug     true = print response data
	 * @return HttpResponse<Capture> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	public HttpResponse<Refund> refundOrder(String captureId, boolean debug) throws IOException, JSONException {
		CapturesRefundRequest request = new CapturesRefundRequest(captureId);
		request.prefer("return=representation");
		request.requestBody(buildRequestBody());
		HttpResponse<Refund> response = client().execute(request);
		if (debug) {
			System.out.println("Status Code: " + response.statusCode());
			System.out.println("Status: " + response.result().status());
			System.out.println("Refund Id: " + response.result().id());
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
	 * This is the driver function which initiates capture refund.
	 *
	 * Capture Id should be replaced with an valid capture id.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new RefundOrder().refundOrder("<<REPLACE-WITH-VALID-CAPTURE-ID>>", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
