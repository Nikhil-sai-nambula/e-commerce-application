package com.example.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ecommerce.model.WishListTo;

public interface WishListRepository extends MongoRepository<WishListTo, String>{
	Optional<WishListTo> findByUserId(String userId);
}
