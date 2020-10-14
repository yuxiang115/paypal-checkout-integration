package com.javatechie.spring.paypal.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BankAccountResponse {
    private String token;
    private String userToken;
    private String type;
    private String status;
    private String verificationStatus;
    private String createdOn;
    private String transferMethodCountry;
    private String transferMethodCurrency;
    private String bankName;
    private String branchId;
    private String bankAccountId;
    private String bankAccountPurpose;
    private String profileType;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String addressLine1;
    private String city;
    private String stateProvince;
    private String country;
    private String postalCode;
}
