package com.javatechie.spring.paypal.api.sample;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersGetRequest;

public class GetOrder extends PayPalClient {

	/**
	 * Method to perform sample GET on an order
	 *
	 * @throws IOException Exceptions from API if any
	 */
	public void getOrder(String orderId) throws IOException, JSONException {
		OrdersGetRequest request = new OrdersGetRequest(orderId);
		HttpResponse<Order> response = client().execute(request);
		System.out.println("Full response body:");
		System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
	}

	/**
	 * This is the driver method which invokes the getOrder function with Order Id
	 * to retrieve an order details.
	 *
	 * To get the correct Order id, we are using the createOrder to create new order
	 * and then we are using the newly created order id.
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, JSONException {
		HttpResponse<Order> response = new CreateOrder().createOrder(false);
		new GetOrder().getOrder(response.result().id());
	}
}
