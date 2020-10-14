package com.javatechie.spring.paypal.api.response.paypal.payout;

import com.paypal.http.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaypalErrorDetail {
        private String field;
        private String issue;
}
