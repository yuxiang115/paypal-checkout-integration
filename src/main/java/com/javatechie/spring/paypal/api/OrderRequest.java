package com.javatechie.spring.paypal.api;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderRequest {

	private double price;
	private String currency;
	private String method;
	private String intent;
	private String description;

}
