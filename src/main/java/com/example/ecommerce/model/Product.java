package com.example.ecommerce.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "Product")
public class Product {

	@Id
	private String id;

	private String name;

	private String oneLiner;

	private String category;

	private List<String> imageURL;

	private String description;
	
	private long ratings;
	
	private long TotalRatings;

	private boolean onSale;

	private double price;

	private double discountPercentage;

}
