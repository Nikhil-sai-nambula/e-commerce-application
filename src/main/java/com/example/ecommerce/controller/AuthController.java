package com.example.ecommerce.controller;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.AuthResponse;
import com.example.ecommerce.model.LoginRequest;
import com.example.ecommerce.model.OTPRequest;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.security.JWTUtil;
import com.example.ecommerce.service.EmailService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private JWTUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {

		if (userRepository.findByEmail(user.getEmail()) != null) {
			return ResponseEntity.badRequest().body("User already Registered");
		}

		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setEmailverified(false);
		user.setOtp(generateOTP());
		user.setOtpGenerateTime(System.currentTimeMillis());
		userRepository.save(user);
		
		emailService.sendSimpleEmail(user.getEmail(), user.getOtp());

		return ResponseEntity.ok("Registered Successfully");

	}

	@PostMapping("/verify-OTP")
	public ResponseEntity<?> verifyOTP(@RequestBody OTPRequest otpRequest) {

		User user = null;

		if (otpRequest.getEmail() != null) {
			
			user = userRepository.findByEmail(otpRequest.getEmail());
			if (user == null) {
				return ResponseEntity.badRequest().body("User not registered");
			}else if (user.getOtpGenerateTime() - System.currentTimeMillis() > 300000) {
				return ResponseEntity.badRequest().body("OTP Expired");
			}else if (!otpRequest.getEmail().equalsIgnoreCase(user.getEmail())) {
				return ResponseEntity.badRequest().body("Invalid OTP");
			}
			
		} else {
			return ResponseEntity.badRequest().body("Missing manditory input fields");
		}

		user.setOtp("");
		user.setEmailverified(true);
		userRepository.save(user);

		return ResponseEntity.ok("OTP verified successfully");

	}

	@PostMapping("/login")
	private ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
		Optional<User> userOpt = Optional.of(userRepository.findByEmail(loginRequest.getEmail()));
		if (userOpt.isEmpty()) {
			return ResponseEntity.status(401).body("Invalid Credentials");
		}

		User user = userOpt.get();
		if (!user.isEmailverified()) {
			return ResponseEntity.status(403).body("Please verify you email first");
		}

		if (!new BCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
			return ResponseEntity.status(401).body("Invalid Credentials");
		}

		String jwtToken = jwtUtil.generateToken(loginRequest.getEmail());
		AuthResponse authResponse = new AuthResponse(user.getName(), user.getEmail(), jwtToken, user.getId());
		return ResponseEntity.ok(authResponse);
	}

	private String generateOTP() {
		return String.valueOf(100000 + new Random().nextInt(900000));
	}

}
