package com.javatechie.spring.paypal.api.request.paypal.payout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class payoutItemsRequest {
    //case number
    private String senderItemId;
    private String note;
    private String receiver;
    private String currency;
    private String amount;
    private String recipientType;
}
