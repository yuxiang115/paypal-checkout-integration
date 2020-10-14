package com.javatechie.spring.paypal.api.request.paypal.payout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class PaypalPayoutBatchRequest {
    private String emailMessage;
    private String emailSubject;
    private String note;
    private String recipientType;
    private List<payoutItemsRequest> payoutItemsRequest;
}
