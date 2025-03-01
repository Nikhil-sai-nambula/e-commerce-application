package com.example.ecommerce.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUploadRequest {

	private String id;

	private String name;

	private String oneLiner;

	private String category;

	private List<MultipartFile> imageURL;

	private String description;

	private long ratings;

	private long TotalRatings;

	private boolean onSale;

	private double price;

	private double discountPercentage;

	private String sellerId;

}
