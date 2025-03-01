package com.example.ecommerce.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductUploadRequest;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.CloudinaryService;
import com.example.ecommerce.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;

	private final ProductRepository productRepository;
	private final CloudinaryService cloudinaryService;

	public ProductController(ProductRepository productRepository, CloudinaryService cloudinaryService) {
		this.productRepository = productRepository;
		this.cloudinaryService = cloudinaryService;
	}
	
	@PostMapping("/seller/product/upload")
    public ResponseEntity<?> uploadProduct(@ModelAttribute ProductUploadRequest request) {
        try {
            List<String> imageUrls = request.getImageURL().stream()
                    .map(file -> {
                        try {
                            return cloudinaryService.uploadImage(file);
                        } catch (IOException e) {
                            throw new RuntimeException("Image upload failed");
                        }
                    })
                    .toList();

            Product product = new Product();
            product.setName(request.getName());
            product.setOneLiner(request.getOneLiner());
            product.setDescription(request.getDescription());
            product.setCategory(request.getCategory());
            product.setPrice(request.getPrice());
            product.setSellerId(request.getSellerId());
            product.setImageURL(imageUrls);

            if (request.getDiscountPercentage() > 0) {
            	product.setDiscountPercentage(request.getDiscountPercentage());
            }
            
            productRepository.save(product);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/seller/product/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @ModelAttribute Product updatedProduct) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(updatedProduct.getName());
            product.setOneLiner(updatedProduct.getOneLiner());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            
            if (updatedProduct.getDiscountPercentage() > 0 && 
            		updatedProduct.getDiscountPercentage() != product.getDiscountPercentage()) {
            	product.setDiscountPercentage(updatedProduct.getDiscountPercentage());
            }
            
            productRepository.save(product);
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/seller/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Product>> getSellerProducts(@PathVariable String sellerId) {
        return ResponseEntity.ok(productRepository.findBySellerId(sellerId));
    }

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
