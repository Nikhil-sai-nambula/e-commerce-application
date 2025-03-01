package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ecommerce.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

	List<Product> findByCategory(String category, Pageable pageable);
	
	List<Product> findBySellerId(String sellerId);

}
