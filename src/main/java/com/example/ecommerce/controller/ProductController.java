package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.service.ProductService;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/")
	public String sayName() {
		return "Hello Mr Nikhil Nambula";
	}
	
	@GetMapping("/products/getAllProducts")
	public List<Product> getAllProducts() {
		List<Product> response = productService.getAllProducts();
		System.out.println(response);
		return response;
	}
}
