package com.example.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ecommerce.model.User;

public interface UserRepository extends MongoRepository<User, String>{

	User findByName(String name);
	
	User findByEmail(String email);
	
}
