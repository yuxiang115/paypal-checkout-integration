package com.javatechie.spring.paypal.api.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultBean {
    private Integer code;
    private String message;
}
