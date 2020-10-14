package com.javatechie.spring.paypal.api.response.paypal.payout;

import com.paypal.http.annotations.SerializedName;
import com.paypal.payouts.ErrorDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaypalError {
    private String debugId;
    private List<PaypalErrorDetail> details;
    private String informationLink;
    private String message;
    private String name;

}
