package com.javatechie.spring.paypal.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayoutRequest {
    private String ammount;
    private String paypalEmail;
}
