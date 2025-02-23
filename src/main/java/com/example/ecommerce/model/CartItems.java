package com.example.ecommerce.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cart")
public class CartItems {

	private String productId;

	private String productName;

	private String productSize;

	private String productCategory;

	private String imageURL;

	private double price;

	private int quantity;

}
