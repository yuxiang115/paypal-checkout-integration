package com.javatechie.spring.paypal.api.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserRequest {
    private String token;
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
}
