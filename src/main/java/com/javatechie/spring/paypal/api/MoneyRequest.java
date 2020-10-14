package com.javatechie.spring.paypal.api;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MoneyRequest {
    private String value;
    private String currency;
}
