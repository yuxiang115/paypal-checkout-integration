package com.javatechie.spring.paypal.api.response.paypal.payout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class CreatePayoutBatchResponse {
    private String batchStatus;
    private PaypalError errors;
    private String payoutBatchId;
    private String timeCreated;
}
