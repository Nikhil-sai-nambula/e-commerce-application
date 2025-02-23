package com.example.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ecommerce.model.Cart;

public interface CartRepository extends MongoRepository<Cart, String>{
	
	Optional<Cart> findByUserId(String userId);

}
