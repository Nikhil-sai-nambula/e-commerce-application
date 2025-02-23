package com.example.ecommerce.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user")
public class User {

	@Id
	private String id;
	private String email;
	private String name;
	private String password;
	private boolean emailverified;
	private String otp;
	private long otpGenerateTime;
	
	@Builder.Default
	private String role = "ROLE_USER";


}
