package com.example.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItems;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.UpdateQuantityRequest;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;

	
	@PostMapping("/add/{userId}")
	public ResponseEntity<Cart> addToCart(@PathVariable String userId, @RequestBody CartItems newCartItem) {
		
		Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
		Cart cart = cartOpt.orElseGet(() -> new Cart(null, userId, new ArrayList<CartItems>(), 0));
		List<CartItems> cartItems = cart.getItems();
		boolean itemExits = false;

		for (CartItems cartItem : cartItems) {
			if (cartItem.getProductId().equalsIgnoreCase(newCartItem.getProductId())) {
				cartItem.setQuantity(cartItem.getQuantity() + newCartItem.getQuantity());
				itemExits = true;
				break;
			}
		}

		if (!itemExits) {
			Optional<Product> product = productRepository.findById(newCartItem.getProductId());
			if (product != null) {
				newCartItem.setImageURL(product.get().getImageURL().get(0));
				newCartItem.setProductName(product.get().getName());
				newCartItem.setProductCategory(product.get().getCategory());
				newCartItem.setPrice(product.get().getPrice());
				newCartItem.setProductCategory(product.get().getCategory());
				
			}
			cartItems.add(newCartItem);
		}

		cart.calculateTotalPrice();
		Cart updateCart = cartRepository.save(cart);
		return ResponseEntity.ok(updateCart);
	}
	

	@GetMapping("/{userId}")
	private ResponseEntity<Cart> getCart(@PathVariable String userId) {
		return cartRepository.findByUserId(userId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	
	@PutMapping("/update-quantity")
	private ResponseEntity<Cart> updatequantity(@RequestBody UpdateQuantityRequest updateQuantityRequest) {
		
		Optional<Cart> cartOpt = cartRepository.findByUserId(updateQuantityRequest.getUserId());
		if (cartOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Cart cart = cartOpt.get();
		for (CartItems cartItem : cart.getItems()) {
			if (cartItem.getProductId().equalsIgnoreCase(updateQuantityRequest.getProductId())) {
				cartItem.setQuantity(updateQuantityRequest.getQuantity());
				break;
			}
		}

		Cart updatedCart = null;
		cart.calculateTotalPrice();
		if (updateQuantityRequest.getQuantity() == 0) {
			cartRepository.delete(cart);
		} else {
			updatedCart = cartRepository.save(cart);
		}

		cart.calculateTotalPrice();
		return ResponseEntity.ok(updatedCart);
	}

	@DeleteMapping("/remove-item")
	public ResponseEntity<Cart> removeItem(@RequestParam String userId, @RequestParam String productId) {
		
		Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
		if (cartOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Cart cart = cartOpt.get();
		cart.getItems().removeIf(item -> item.getProductId().equals(productId));
		cart.calculateTotalPrice();
		if (cart.getItems().isEmpty()) {
			cartRepository.delete(cart);
			return ResponseEntity.ok().build();
		}

		Cart updatedCart = cartRepository.save(cart);
		return ResponseEntity.ok(updatedCart);
	}

	
}
