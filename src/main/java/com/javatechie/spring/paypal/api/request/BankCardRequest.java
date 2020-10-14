package com.javatechie.spring.paypal.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankCardRequest {
    private String userToken;
    private String cardNumber;
    private String dateOfExpiry; // yyyy-mm
    private String cvv;
    private String transferMethodCountry;
    private String transferMethodCurrency;
    private String type;
}
