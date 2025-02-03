package com.example.ecommerce.service;

import org.springframework.data.domain.Pageable; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.repository.ProductRepository;

@Component
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	public List<Product> getAllProducts(){
		Pageable pageable = (Pageable) PageRequest.of(0, 9); // No limit
		List<Product> response = productRepository.findAll();
		if (response == null) {
			throw new RuntimeException("Products Not Found");
		}
		return response;
	}
}
