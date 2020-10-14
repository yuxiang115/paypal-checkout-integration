package com.javatechie.spring.paypal.api.sample;

import java.io.IOException;

import com.paypal.orders.*;
import org.json.JSONException;
import org.json.JSONObject;

import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;

public class AuthorizeOrder extends PayPalClient {

	/**
	 * Building empty request body. This can be updated with required fields as per
	 * need.
	 *
	 * @return OrderActionRequest with empty body
	 */
	private OrderRequest buildRequestBody() {
		return new OrderRequest();
	}

	/**
	 * Method to authorize order after creation
	 *
	 * @param orderId Valid Approved Order ID from createOrder response
	 * @param debug   true = print response data
	 * @return HttpResponse<Order> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	public HttpResponse<Order> authorizeOrder(String orderId, boolean debug) throws IOException, JSONException {
		OrdersAuthorizeRequest request = new OrdersAuthorizeRequest(orderId);
		request.requestBody(buildRequestBody());
		HttpResponse<Order> response = client().execute(request);
		if (debug) {
			System.out.println("Authorization Ids:");
			response.result().purchaseUnits().forEach(purchaseUnit -> purchaseUnit.payments().authorizations().stream()
					.map(authorization -> authorization.id()).forEach(System.out::println));
			System.out.println("Link Descriptions: ");
			for (LinkDescription link : response.result().links()) {
				System.out.println("\t" + link.rel() + ": " + link.href());
			}
			System.out.println("Full response body:");
			System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
		}
		return response;
	}

	/**
	 * This is the driver function which invokes the authorizeOrder function to
	 * create an sample order.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new AuthorizeOrder().authorizeOrder("<<REPLACE-WITH-APPROVED-ORDER-ID>>", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
