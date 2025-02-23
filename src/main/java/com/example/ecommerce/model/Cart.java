package com.example.ecommerce.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cart")
public class Cart {

	@Id
	private String id;
	private String userId;
	private List<CartItems> items;
	private double totalPrice;

	public void calculateTotalPrice() {
		this.totalPrice = items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
	}
}
