package com.javatechie.spring.paypal.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PrepaidCardRequest {
    private String token;
    private String userToken;
    private String cardPackage;
    private String type;
}
