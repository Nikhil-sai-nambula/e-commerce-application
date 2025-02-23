package com.example.ecommerce.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishListRequest {

	private String productId;
	
	private String productName;

	private String productCategory;

	private String productOneLiner;
	
	private String imageURL;

	private double price;

}
