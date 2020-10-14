package com.javatechie.spring.paypal.api.response.paypal.payout;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class PayoutBatchItemDto {
    private String activityId;
    private String payoutBatchId;
    private Currency amount;
    private String note;
    private String receiver;
    private String recipientName;
    private String recipientType;
    private String recipientWallet;
    private String senderItemId;
    private Currency payoutItemFee;
    private PaypalError errors;
    private String payoutItemId;
    private String timeProcessed;
    private String transactionId;
    private String transactionStatus;
}
