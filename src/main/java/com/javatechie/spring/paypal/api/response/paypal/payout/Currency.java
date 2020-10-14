package com.javatechie.spring.paypal.api.response.paypal.payout;

import com.paypal.http.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Currency {
    private String currency;
    private String value;
}
