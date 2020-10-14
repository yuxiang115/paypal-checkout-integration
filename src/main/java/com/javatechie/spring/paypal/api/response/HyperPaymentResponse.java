package com.javatechie.spring.paypal.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HyperPaymentResponse {
    private String token;
    private String status;
    private String createdOn;
    private String amount;
    private String currency;
    private String clientPaymentId;
    private String purpose;
    private String releaseOn;
    private String expiresOn;
    private String destinationToken;
    private String programToken;

}
