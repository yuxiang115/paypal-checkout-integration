package com.javatechie.spring.paypal.api.response.paypal.payout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PayoutBatchResponse {
    private Currency amount;
    private String batchStatus;
    private Currency fees;
    private String fundingSource;
    private String payoutBatchId;
    private String timeClosed;
    private String timeCompleted;
    private String timeCreated;

    private List<PayoutBatchItemDto> items;
    private Integer totalItems;
    private Integer totalPages;

}
