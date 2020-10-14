package com.javatechie.spring.paypal.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HyperPaymentRequest {
    private String token;
    private String amount;
    private String clientPaymentId;
    private String currency;
    private String destinationToken;
    private String purpose;


}
