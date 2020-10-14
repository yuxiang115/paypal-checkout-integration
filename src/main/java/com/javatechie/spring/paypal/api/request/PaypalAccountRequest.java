package com.javatechie.spring.paypal.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PaypalAccountRequest {
    private String userToken;
    private String type;
    private String transferMethodCountry;
    private String transferMethodCurrency;
    private String email;
}
