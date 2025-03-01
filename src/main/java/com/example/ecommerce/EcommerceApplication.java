package com.example.ecommerce;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		List<Integer> arr = new ArrayList<Integer>();
		String name = "Aa";
		String name1 = "BB";
		System.out.println(name.hashCode() + " " + name1.hashCode());
		System.out.println(name.hashCode());
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
