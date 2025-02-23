package com.example.ecommerce.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wishlist")
public class WishListItems {

	private String productId;

	private String productName;

	private String productCategory;
	
	private String productOneLiner;

	private String imageURL;

	private double price;

}
