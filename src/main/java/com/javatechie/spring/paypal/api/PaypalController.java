package com.javatechie.spring.paypal.api;

import com.javatechie.spring.paypal.api.request.PayoutRequest;
import com.javatechie.spring.paypal.api.request.paypal.payout.PaypalPayoutBatchRequest;
import com.javatechie.spring.paypal.api.response.paypal.payout.CreatePayoutBatchResponse;
import com.javatechie.spring.paypal.api.response.paypal.payout.PayoutBatchItemDto;
import com.javatechie.spring.paypal.api.response.paypal.payout.PayoutBatchResponse;
import com.paypal.http.HttpResponse;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.PaymentCollection;
import com.paypal.orders.PurchaseUnit;
import com.paypal.payments.Refund;
import com.paypal.payouts.CreatePayoutResponse;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PaypalController {

    @Autowired
    PaypalService service;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/pay")
    public String payment(@ModelAttribute("order") OrderRequest orderRequest) {

        HttpResponse<Order> orderHttpResponse = service.createOrder(orderRequest);
        if (orderHttpResponse != null) {
            Order order = orderHttpResponse.result();
            for (LinkDescription link : order.links()) {
                if (link.rel().equals("approve")) {
                    return "redirect:" + link.href();
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    @ResponseBody
    public List<String> successPay(@RequestParam("token") String orderId, @RequestParam("PayerID") String payerId, @RequestParam("caseNum") String caseNum) {
        Order order= service.captureOrder(orderId);
        List<PurchaseUnit> purchaseUnits = order.purchaseUnits();
        List<String> res = new ArrayList<>();
        purchaseUnits.stream().filter(purchaseUnit -> purchaseUnit != null && purchaseUnit.payments() != null).forEach(purchaseUnit -> {
            res.add(purchaseUnit.payments().captures().get(0).id());
        });
        return res;
    }

/*      @GetMapping(value = "pay/capture_order")
        @ResponseBody
        public Order refund(@RequestParam("order_id") String orderId){
            HttpResponse<Order> orderHttpResponse = service.captureOrder(orderId);
            Order order = orderHttpResponse.result();

            return order;
        }*/

    @GetMapping(value = "pay/refundAll")
    @ResponseBody
    public Refund refundAll(@RequestParam("captureId") String captureId){
        HttpResponse<Refund> refundHttpResponse = service.refundAll(captureId);
        return refundHttpResponse.result();
    }


    @GetMapping(value = "pay/getOrder")
    @ResponseBody
    public List<String> getPayment(@RequestParam("orderId") String orderId){
        Order order = service.getOrder(orderId).result();
        List<String> res = new ArrayList<>();

        order.purchaseUnits().stream().filter(purchaseUnit -> purchaseUnit != null && purchaseUnit.payments() != null).forEach(purchaseUnit -> {
            purchaseUnit.payments().captures().stream().filter(capture -> capture != null).forEach(capture -> res.add(capture.id()));
        });
        return res;
    }

    @PostMapping(value = "/payouts/create")
    @ResponseBody
    public CreatePayoutBatchResponse createPayout(@RequestBody PaypalPayoutBatchRequest paypalPayoutBatchRequest) throws IOException, JSONException {
        return service.createPayoutBatch(paypalPayoutBatchRequest);
    }

    @GetMapping(value = "/payouts/getBatch")
    @ResponseBody
    public PayoutBatchResponse getPayoutBatch(@RequestParam String batchId, @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required = false, defaultValue = "false") boolean totalRequired) throws IOException, JSONException {
        return service.getPayoutBatch(batchId, page, pageSize, totalRequired);
    }

    @GetMapping(value = "/payouts/getItem")
    @ResponseBody
    public PayoutBatchItemDto getPayoutItem(@RequestParam String itemId) throws IOException, JSONException {
        return service.getPayoutItem(itemId);
    }

    @GetMapping(value = "/payouts/cancelItem")
    @ResponseBody
    public PayoutBatchItemDto cancelPayoutItem(@RequestParam String itemId) throws IOException, JSONException {
        return service.cancelPayoutItem(itemId);
    }

}
