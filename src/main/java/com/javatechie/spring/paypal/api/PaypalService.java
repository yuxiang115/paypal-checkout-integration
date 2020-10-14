package com.javatechie.spring.paypal.api;

import com.javatechie.spring.paypal.api.Util.paypal.PaypalClient;
import com.javatechie.spring.paypal.api.request.paypal.payout.PaypalPayoutBatchRequest;
import com.javatechie.spring.paypal.api.response.paypal.payout.*;
import com.javatechie.spring.paypal.api.response.paypal.payout.Currency;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import com.paypal.orders.PurchaseUnit;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.payments.Refund;
import com.paypal.payouts.*;
import com.paypal.payouts.Error;
import org.dozer.Mapper;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class PaypalService {
	@Autowired
	private Mapper mapper;

	public HttpResponse<Order> createOrder(OrderRequest orderRequest){
		HttpResponse<Order> order = null;
		try{
			order = PaypalClient.createOrder(orderRequest);
		} catch (com.paypal.http.exceptions.HttpException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	public Order captureOrder(String orderId){
		HttpResponse<Order> order = null;

		try{
			List<PurchaseUnit> purchaseUnits =  PaypalClient.getOrder(orderId).result().purchaseUnits();
			for(PurchaseUnit purchaseUnit : purchaseUnits){
				if(purchaseUnit != null) {
					PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest();
					purchaseUnitRequest.amountWithBreakdown(purchaseUnit.amountWithBreakdown());
					purchaseUnitRequest.description(purchaseUnit.description());
					order = PaypalClient.captureOrder(orderId, purchaseUnitRequest);
				}
			}

		} catch (com.paypal.http.exceptions.HttpException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order.result();
	}

	public HttpResponse<Refund> refundAll(String captureId){
		HttpResponse<Refund> refund = null;
		try{
			refund = PaypalClient.refundAll(captureId);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return refund;
	}

	public HttpResponse<Order> getOrder(String orderId)  {
		HttpResponse<Order> order = null;
		try{
			order = PaypalClient.getOrder(orderId);
		} catch (com.paypal.http.exceptions.HttpException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}




	/**======================================Payout=================================================**/
	public CreatePayoutBatchResponse createPayoutBatch(PaypalPayoutBatchRequest paypalPayoutBatchRequest) throws JSONException, IOException {
		HttpResponse<CreatePayoutResponse> createPayoutResponseHttpResponse = null;
			createPayoutResponseHttpResponse = PaypalClient.createPayoutBatch(paypalPayoutBatchRequest);
		PayoutHeader payoutHeader = createPayoutResponseHttpResponse.result().batchHeader();

		CreatePayoutBatchResponse createPayoutBatchResponse = new CreatePayoutBatchResponse();
		createPayoutBatchResponseMapper(payoutHeader, createPayoutBatchResponse);
		return createPayoutBatchResponse;
	}

	private void createPayoutBatchResponseMapper(PayoutHeader payoutHeader, CreatePayoutBatchResponse createPayoutBatchResponse){
		createPayoutBatchResponse.setBatchStatus(payoutHeader.batchStatus());
		createPayoutBatchResponse.setPayoutBatchId(payoutHeader.payoutBatchId());
		createPayoutBatchResponse.setTimeCreated(payoutHeader.timeCreated());
		if(payoutHeader.errors() != null) {
			PaypalError paypalError = new PaypalError();
			PaypalErrorMapper(payoutHeader.errors(), paypalError);
			createPayoutBatchResponse.setErrors(paypalError);
		}

	}

	private void PaypalErrorMapper(Error error, PaypalError paypalError){
		paypalError.setDebugId(error.debugId());
		List<PaypalErrorDetail> paypalErrorDetails = new ArrayList<>();
		error.details().stream().forEach(errorDetail -> {
			PaypalErrorDetail detail = new PaypalErrorDetail();
			PaypalErrorDetailsMapper(errorDetail, detail);
			paypalErrorDetails.add(detail);
		});
		paypalError.setDetails(paypalErrorDetails);
		paypalError.setInformationLink(error.informationLink());
		paypalError.setMessage(error.message());
		paypalError.setName(error.name());
	}

	private void PaypalErrorMapper(PayoutError error, PaypalError paypalError){
		paypalError.setDebugId(error.debugId());
		List<PaypalErrorDetail> paypalErrorDetails = new ArrayList<>();
		error.payoutErrorDetails().stream().forEach(errorDetail -> {
			PaypalErrorDetail detail = new PaypalErrorDetail();
			PaypalErrorDetailsMapper(errorDetail, detail);
			paypalErrorDetails.add(detail);
		});
		paypalError.setDetails(paypalErrorDetails);
		paypalError.setInformationLink(error.informationLink());
		paypalError.setMessage(error.message());
		paypalError.setName(error.name());
	}

	private void PaypalErrorDetailsMapper(ErrorDetails detail, PaypalErrorDetail paypalErrorDetail){
			paypalErrorDetail.setField(detail.field());
			paypalErrorDetail.setIssue(detail.field());
	}

	private void PaypalErrorDetailsMapper(PayoutErrorDetails detail, PaypalErrorDetail paypalErrorDetail){
			paypalErrorDetail.setField(detail.field());
			paypalErrorDetail.setIssue(detail.field());
	}


	public PayoutBatchResponse getPayoutBatch(String batchId, Integer page, Integer pageSize, boolean totalRequired) throws IOException, JSONException {
		HttpResponse<PayoutBatch> response = null;
		PayoutBatchResponse payoutBatchResponse = new PayoutBatchResponse();

		try {
			response = PaypalClient.getPayoutBatch(batchId, page, pageSize, totalRequired);
			PayoutBatch payoutBatch = response.result();
			PayoutBatchResponseMapper(payoutBatch, payoutBatchResponse);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return payoutBatchResponse;
	}

	private void PayoutBatchResponseMapper(PayoutBatch payoutBatch, PayoutBatchResponse payoutBatchResponse){
		payoutBatchResponse.setAmount(getCurrencyMapper(payoutBatch.batchHeader().amount()));
		payoutBatchResponse.setBatchStatus(payoutBatch.batchHeader().batchStatus());
		payoutBatchResponse.setFees(getCurrencyMapper(payoutBatch.batchHeader().fees()));
		payoutBatchResponse.setFundingSource(payoutBatch.batchHeader().fundingSource());
		payoutBatchResponse.setPayoutBatchId(payoutBatch.batchHeader().payoutBatchId());
		payoutBatchResponse.setTimeClosed(payoutBatch.batchHeader().timeClosed());
		payoutBatchResponse.setTimeCompleted(payoutBatch.batchHeader().timeCompleted());
		payoutBatchResponse.setTimeCreated(payoutBatch.batchHeader().timeCreated());
		payoutBatchResponse.setTotalItems(payoutBatch.totalItems());
		payoutBatchResponse.setTotalPages(payoutBatch.totalPages());
		if(payoutBatch.items() != null && !payoutBatch.items().isEmpty()){
			List<PayoutBatchItemDto> payoutBatchItemDtos = new ArrayList<>();
			payoutBatch.items().stream().forEach(item->{
				PayoutBatchItemDto payoutBatchItemDto = new PayoutBatchItemDto();
				PayoutBatchItemDtoMapper(item, payoutBatchItemDto);
				payoutBatchItemDtos.add(payoutBatchItemDto);
			});
			payoutBatchResponse.setItems(payoutBatchItemDtos);
		}

	}

	private void PayoutBatchItemDtoMapper(PayoutBatchItem payoutBatchItem, PayoutBatchItemDto payoutBatchItemDto){
		payoutBatchItemDto.setActivityId(payoutBatchItem.activityId());
		payoutBatchItemDto.setAmount(getCurrencyMapper(payoutBatchItem.payoutItem().amount()));
		payoutBatchItemDto.setNote(payoutBatchItem.payoutItem().note());
		payoutBatchItemDto.setPayoutBatchId(payoutBatchItem.payoutBatchId());
		payoutBatchItemDto.setPayoutItemFee(getCurrencyMapper(payoutBatchItem.payoutItemFee()));
		payoutBatchItemDto.setPayoutItemId(payoutBatchItem.payoutItemId());
		payoutBatchItemDto.setReceiver(payoutBatchItem.payoutItem().receiver());
		if(payoutBatchItem.payoutItem().recipientName() != null && payoutBatchItem.payoutItem().recipientName().fullName() != null)
			payoutBatchItemDto.setRecipientName(payoutBatchItem.payoutItem().recipientName().fullName());
		payoutBatchItemDto.setRecipientType(payoutBatchItem.payoutItem().recipientType());
		payoutBatchItemDto.setSenderItemId(payoutBatchItem.payoutItem().senderItemId());
		payoutBatchItemDto.setTransactionId(payoutBatchItem.transactionId());
		payoutBatchItemDto.setTransactionStatus(payoutBatchItem.transactionStatus());
		payoutBatchItemDto.setTimeProcessed(payoutBatchItem.timeProcessed());
		if(payoutBatchItem.errors() != null) {
			PaypalError paypalError = new PaypalError();
			this.PaypalErrorMapper(payoutBatchItem.errors(), paypalError);
			payoutBatchItemDto.setErrors(paypalError);
		}
	}

	private void PayoutBatchItemDtoMapper(PayoutItemResponse payoutBatchItem, PayoutBatchItemDto payoutBatchItemDto){
		payoutBatchItemDto.setActivityId(payoutBatchItem.activityId());
		payoutBatchItemDto.setAmount(getCurrencyMapper(payoutBatchItem.payoutItem().amount()));
		payoutBatchItemDto.setNote(payoutBatchItem.payoutItem().note());
		payoutBatchItemDto.setPayoutBatchId(payoutBatchItem.payoutBatchId());
		payoutBatchItemDto.setPayoutItemFee(getCurrencyMapper(payoutBatchItem.payoutItemFee()));
		payoutBatchItemDto.setPayoutItemId(payoutBatchItem.payoutItemId());
		payoutBatchItemDto.setReceiver(payoutBatchItem.payoutItem().receiver());
		if(payoutBatchItem.payoutItem().recipientName() != null && payoutBatchItem.payoutItem().recipientName().fullName() != null)
			payoutBatchItemDto.setRecipientName(payoutBatchItem.payoutItem().recipientName().fullName());
		payoutBatchItemDto.setRecipientType(payoutBatchItem.payoutItem().recipientType());
		payoutBatchItemDto.setSenderItemId(payoutBatchItem.payoutItem().senderItemId());
		payoutBatchItemDto.setTransactionId(payoutBatchItem.transactionId());
		payoutBatchItemDto.setTransactionStatus(payoutBatchItem.transactionStatus());
		payoutBatchItemDto.setTimeProcessed(payoutBatchItem.timeProcessed());
		if(payoutBatchItem.errors() != null) {
			PaypalError paypalError = new PaypalError();
			this.PaypalErrorMapper(payoutBatchItem.errors(), paypalError);
			payoutBatchItemDto.setErrors(paypalError);
		}
	}

	private Currency getCurrencyMapper(com.paypal.payouts.Currency cur){
		Currency currency = new Currency();
		currency.setCurrency(cur.currency());
		currency.setValue(cur.value());
		return currency;
	}

	public PayoutBatchItemDto getPayoutItem(String payoutItemId) throws IOException, JSONException {
		HttpResponse<PayoutItemResponse> response = null;
		PayoutBatchItemDto payoutBatchItemDto = new PayoutBatchItemDto();

		try {
			response = PaypalClient.getPayoutItem(payoutItemId);
			PayoutBatchItemDtoMapper(response.result(), payoutBatchItemDto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return payoutBatchItemDto;
	}

	public PayoutBatchItemDto cancelPayoutItem(String payoutItemId) throws IOException, JSONException {
		HttpResponse<PayoutItemResponse> response = null;
		PayoutBatchItemDto payoutBatchItemDto = new PayoutBatchItemDto();

		try {
			response = PaypalClient.cancelPayoutItem(payoutItemId);
			PayoutBatchItemDtoMapper(response.result(), payoutBatchItemDto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return payoutBatchItemDto;
	}
}
