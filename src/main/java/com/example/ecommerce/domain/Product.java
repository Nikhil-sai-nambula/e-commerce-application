package com.example.ecommerce.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Product")
public class Product {
	
	@Id
	private String id;

	private String name;
	
	private String oneLiner;

	private String category;

	private List<String> imageURL;

	private String description;

	private boolean onSale;
	
	private double price;
	
	private double discountPercentage;
	

	public Product(String id, String name, String oneLiner, String category, List<String> imageURL, String description,
			boolean onSale, double price, double discountPercentage) {
		this.id = id;
		this.name = name;
		this.oneLiner = oneLiner;
		this.category = category;
		this.imageURL = imageURL;
		this.description = description;
		this.onSale = onSale;
		this.price = price;
		this.discountPercentage = discountPercentage;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", oneLiner=" + oneLiner + ", category=" + category
				+ ", imageURL=" + imageURL + ", description=" + description + ", onSale=" + onSale + ", price=" + price
				+ ", discountPercentage=" + discountPercentage + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOneLiner() {
		return oneLiner;
	}

	public void setOneLiner(String oneLiner) {
		this.oneLiner = oneLiner;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getImageURL() {
		return imageURL;
	}

	public void setImageURL(List<String> imageURL) {
		this.imageURL = imageURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isOnSale() {
		return onSale;
	}

	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

}
