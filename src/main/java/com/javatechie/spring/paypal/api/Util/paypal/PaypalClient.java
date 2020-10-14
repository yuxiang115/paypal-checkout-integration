package com.javatechie.spring.paypal.api.Util.paypal;

import com.javatechie.spring.paypal.api.request.paypal.payout.PaypalPayoutBatchRequest;
import com.javatechie.spring.paypal.api.request.paypal.payout.payoutItemsRequest;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.*;
import com.paypal.orders.LinkDescription;
import com.paypal.payments.CapturesRefundRequest;
import com.paypal.payments.RefundRequest;
import com.paypal.payouts.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class PaypalClient {
    private static boolean debug = true;

/*    private static OrderRequest buildMinimumRequestBody(com.javatechie.spring.paypal.api.OrderRequest comingOrder) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("AUTHORIZE");

        ApplicationContext applicationContext =
                new ApplicationContext().brandName("Test INC")
                        .landingPage("BILLING")
                        .cancelUrl("http://localhost:9090/pay/cancel")
                        .returnUrl("http://localhost:9090/pay/success")
                        .userAction("Continue");

        orderRequest.applicationContext(applicationContext);


        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode(comingOrder.getCurrency()).value(String.valueOf(comingOrder.getPrice())));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }*/
    private static OrderRequest buildOrderRequestBody(){
    return new OrderRequest();
}

    private static OrderRequest buildMinimumOrderRequestBody(com.javatechie.spring.paypal.api.OrderRequest comingOrder) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        ApplicationContext applicationContext = new ApplicationContext()
                .cancelUrl("http://localhost:9090/pay/cancel").returnUrl("http://localhost:9090/pay/success?caseNum=123");
        orderRequest.applicationContext(applicationContext);
        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId("FIRST_PURCHASE").description(comingOrder.getDescription())
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode(comingOrder.getCurrency()).value(String.valueOf(comingOrder.getPrice())));


        PurchaseUnitRequest purchaseUnitRequest1 = new PurchaseUnitRequest().referenceId("SECOND_PURCHASE")
                .description("Other 0.5 dollar").amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value("0.5"));

        purchaseUnitRequests.add(purchaseUnitRequest);
        purchaseUnitRequests.add(purchaseUnitRequest1);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

    private static RefundRequest buildRefundAllRequestBody(){
        RefundRequest refundRequest = new RefundRequest();
        return refundRequest;
    }

    public static HttpResponse<Order> createOrder(com.javatechie.spring.paypal.api.OrderRequest comingOrder) throws IOException, JSONException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.header("prefer", "return=representation");
        request.requestBody(buildMinimumOrderRequestBody(comingOrder));
        HttpResponse<Order> response = new Credentials().CLIENT.execute(request);
        if (debug) {
            if (response.statusCode() == 201) {
                System.out.println("Order with Complete Payload: ");
                System.out.println("Status Code: " + response.statusCode());
                System.out.println("Status: " + response.result().status());
                System.out.println("Order ID: " + response.result().id());
                System.out.println("Intent: " + response.result().checkoutPaymentIntent());
                System.out.println("Links: ");
                for (LinkDescription link : response.result().links()) {
                    System.out.println(link.rel() + ": " + link.href() + "     Call Type: " + link.method());
                }
                System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode()
                        + " " + response.result().purchaseUnits().get(0).amountWithBreakdown().value());
                System.out.println("Full response body:");
                System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
            }
        }
        return response;
    }



    private static OrderRequest buildCaputureOrderRequestBody(PurchaseUnitRequest purchaseUnitRequest ){
        OrderRequest orderRequest = new OrderRequest();
        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

    public static HttpResponse<Order> captureOrder(String orderId, PurchaseUnitRequest purchaseUnitRequest) throws IOException, JSONException {
        OrdersCaptureRequest orderCaptureRequest = new OrdersCaptureRequest(orderId);
        orderCaptureRequest.requestBody(buildCaputureOrderRequestBody(purchaseUnitRequest));
        HttpResponse<Order> response = Credentials.CLIENT.execute(orderCaptureRequest);

        if (debug) {
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Status: " + response.result().status());
            System.out.println("Order ID: " + response.result().id());
            System.out.println("Links: ");
            for (LinkDescription link : response.result().links()) {
                System.out.println("" + link.rel() + ": " + link.href());
            }
            System.out.println("Capture ids:");
            for (PurchaseUnit purchaseUnit : response.result().purchaseUnits()) {
                for (Capture capture : purchaseUnit.payments().captures()) {
                    System.out.println("  " + capture.id());
                }
            }
            System.out.println("Buyer: ");
            Payer buyer = response.result().payer();
            System.out.println("\tEmail Address: " + buyer.email());
            System.out.println("\tName: " + buyer.name().givenName() + " " + buyer.name().surname());
            System.out.println("Full response body:");
            System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        }

        return response;
    }

    public static HttpResponse<com.paypal.payments.Refund> refundAll(String captureId) throws IOException, JSONException {
        CapturesRefundRequest request = new CapturesRefundRequest(captureId);
        request.prefer("return=representation");
        request.requestBody(buildRefundAllRequestBody());
        HttpResponse<com.paypal.payments.Refund> response = Credentials.CLIENT.execute(request);
        if (debug) {
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Status: " + response.result().status());
            System.out.println("Refund Id: " + response.result().id());
            System.out.println("Links: ");
            for (com.paypal.payments.LinkDescription link : response.result().links()) {
                System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
            }
            System.out.println("Full response body:");
            System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        }
        return response;
    }

    public static HttpResponse<Order> getOrder(String orderId) throws IOException, JSONException {
        OrdersGetRequest request = new OrdersGetRequest(orderId);
        HttpResponse<Order> response = Credentials.CLIENT.execute(request);
       if(debug) {
           System.out.println("Full response body:");
           System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
       }
        return response;
    }

/*****************************************************Payout****************************************************/

    private static PayoutsPostRequest buildPayoutBatchRequestBody(PaypalPayoutBatchRequest paypalPayoutBatchRequest){
        List<PayoutItem> items = new ArrayList<>();
        List<payoutItemsRequest> payoutItemsRequests = paypalPayoutBatchRequest.getPayoutItemsRequest();
        payoutItemsRequests.stream().forEach(itemsRequest ->{
            PayoutItem item = new PayoutItem().senderItemId(itemsRequest.getSenderItemId())
                    .note(itemsRequest.getNote())
                    .receiver(itemsRequest.getReceiver())
                    .recipientType(itemsRequest.getRecipientType())
                    .amount(new Currency().currency(itemsRequest.getCurrency()).value(itemsRequest.getAmount()));
            items.add(item);
        });

        OffsetDateTime now = OffsetDateTime.now( ZoneOffset.UTC);
        CreatePayoutRequest payoutRequestBody = new CreatePayoutRequest()
                .senderBatchHeader(new SenderBatchHeader()
                    .senderBatchId(now.toString())
                    .emailMessage(paypalPayoutBatchRequest.getEmailMessage())
                    .emailSubject(paypalPayoutBatchRequest.getEmailSubject())
                    .note(paypalPayoutBatchRequest.getEmailSubject()))
                .items(items);

        return new PayoutsPostRequest().requestBody(payoutRequestBody);
    }

    public static HttpResponse<CreatePayoutResponse> createPayoutBatch(PaypalPayoutBatchRequest paypalPayoutBatchRequest) throws IOException, JSONException {
        PayoutsPostRequest request = buildPayoutBatchRequestBody(paypalPayoutBatchRequest);

        HttpResponse<CreatePayoutResponse> response = Credentials.CLIENT.execute(request);
        System.out.println("===========================Create Payout============================================");
        System.out.println("Response Body:");
        System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        return response;
    }

    public static HttpResponse<PayoutBatch> getPayoutBatch(String batchId, Integer page, Integer pageSize, boolean totalRequired) throws IOException, JSONException {
        PayoutsGetRequest request = new PayoutsGetRequest(batchId)
                .page(page)
                .pageSize(pageSize)
                .totalRequired(totalRequired);
        HttpResponse<PayoutBatch> response = Credentials.CLIENT.execute(request);

        System.out.println("===========================Get Payout Batch============================================");
        System.out.println("Response Body:");
        System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));

        return response;
    }


    public static HttpResponse<PayoutItemResponse> getPayoutItem(String itemId) throws IOException, JSONException {
        PayoutsItemGetRequest request = new PayoutsItemGetRequest(itemId);
        HttpResponse<PayoutItemResponse> response = Credentials.CLIENT.execute(request);

        System.out.println("===========================Get Payout Item============================================");
        System.out.println("Response Body:");
        System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        return response;
    }

    public static HttpResponse<PayoutItemResponse> cancelPayoutItem(String itemId) throws IOException, JSONException {
        PayoutsItemCancelRequest request = new PayoutsItemCancelRequest(itemId);
        HttpResponse<PayoutItemResponse> response = Credentials.CLIENT.execute(request);

        System.out.println("===========================Cancel Payout Item============================================");
        System.out.println("Response Body:");
        System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        return response;
    }

}
