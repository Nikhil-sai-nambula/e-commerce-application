package com.example.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.User;
import com.example.ecommerce.model.WishListItems;
import com.example.ecommerce.model.WishListTo;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.repository.WishListRepository;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WishListRepository wishListRepository;

	@GetMapping("/get-wishlist-items/{userId}")
	public ResponseEntity<?> getWishListItems(@PathVariable String userId) {
		Optional<User> userOpt = userRepository.findById(userId);

		if (userOpt.isEmpty()) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
		}

		Optional<WishListTo> wishListTo = wishListRepository.findByUserId(userId);

		if (wishListTo.isEmpty()) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("WishList Not Found");
		}

		return ResponseEntity.ok(wishListTo);
	}

	@PostMapping("/add/{userId}")
	public ResponseEntity<?> addtoWishList(@PathVariable String userId, @RequestBody WishListRequest wishListRequest) {
		Optional<User> userOpt = userRepository.findById(userId);

		if (userOpt.isEmpty()) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
		}

		Optional<WishListTo> wishListToOpt = wishListRepository.findByUserId(userId);
		
		WishListItems wishListItems = new WishListItems();
		wishListItems.setProductId(wishListRequest.getProductId());
		wishListItems.setProductName(wishListRequest.getProductName());
		wishListItems.setProductCategory(wishListRequest.getProductCategory());
		wishListItems.setImageURL(wishListRequest.getImageURL());
		wishListItems.setPrice(wishListRequest.getPrice());
		wishListItems.setProductOneLiner(wishListRequest.getProductOneLiner());
		
		WishListTo savedItems = null;
		if (!wishListToOpt.isEmpty()) {
			
			WishListTo wishListTo = wishListToOpt.get();
			List<WishListItems> wishListItemsOpt = wishListTo.getProducts();
			wishListItemsOpt.add(wishListItems);
			wishListTo.setProducts(wishListItemsOpt);
			savedItems = wishListRepository.save(wishListTo);
			
		}
		else {
		    WishListTo newWishList = new WishListTo();
		    newWishList.setUserId(userId);
		    newWishList.setProducts(List.of(wishListItems));
		    savedItems = wishListRepository.save(newWishList);
		}
		
		if (savedItems != null) {
			return ResponseEntity.ok(savedItems.getProducts());
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Persisting wishlist details failed");
	}
	
	
	@DeleteMapping("/remove/{userId}")
	public ResponseEntity<?> removeFromWishList(@PathVariable String userId, @RequestBody String productId) {
		Optional<User> userOpt = userRepository.findById(userId);

		if (userOpt.isEmpty()) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
		}

		Optional<WishListTo> wishListToOpt = wishListRepository.findByUserId(userId);
		
		if (wishListToOpt.isEmpty()) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("WishList Not Found");
		}
		
		String IdToBeDeleted = productId.substring(1, productId.length() - 1);
		
		List<WishListItems> wishListItems = wishListToOpt.get().getProducts();
		wishListItems.removeIf(item  -> item.getProductId().equals(IdToBeDeleted));
		
		if (wishListItems.isEmpty()) {
			wishListRepository.delete(wishListToOpt.get());
		    return ResponseEntity.ok("Wishlist is now empty");
		}

		wishListToOpt.get().setProducts(wishListItems);
		WishListTo savedWishList = wishListRepository.save(wishListToOpt.get());
		
		return ResponseEntity.ok(savedWishList);
	}

}
