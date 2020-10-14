package com.javatechie.spring.paypal.api.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String token;
    private String status;
    private String verificationStatus;
    private String createdOn;
    private String clientUserId;
    private String profileType;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String addressLine1;
    private String city;
    private String stateProvince;
    private String country;
    private String postalCode;
    private String language;
    private String programToken;

}
