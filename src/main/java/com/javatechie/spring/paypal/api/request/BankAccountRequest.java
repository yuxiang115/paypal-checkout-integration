package com.javatechie.spring.paypal.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BankAccountRequest {
    private String token;
    private String userToken;
    private String profileType;
    private String transferMethodCountry;
    private String transferMethodCurrency;
    private String type;
    private String branchId;
    private String bankAccountId;
    private String bankAccountPurpose;
    private String firstName;
    private String lastName;
    private String country;
    private String stateProvince;
    private String addressLine1;
    private String city;
    private String postalCode;
    private String bankAccountRelationship;
}
