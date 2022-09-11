package levin.schnabel.catalogService.behavioral.controller;

import levin.schnabel.catalogService.behavioral.service.ProductService;
import levin.schnabel.catalogService.structural.entity.Product;
import levin.schnabel.catalogService.structural.exception.ProductServiceExpception;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class GetProductsController {

	private final ProductService productService;

	public GetProductsController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping(
			path = "product",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> create(@RequestBody List<String> idList) {
		return ResponseEntity.ok(productService.getProductsById(idList));
	}

	@GetMapping("/product/{id}")
	public Product getProduct(@PathVariable String id) {
		try {
			return productService.getProductById(id);
		} catch (ProductServiceExpception e) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Could not find product with id " + id
			);
		}
	}

	@GetMapping("/category/{category}")
	public List<Product> getProductByCategory(@PathVariable String category) {
		try {
			return productService.getProductsByCategory(category);
		} catch (ProductServiceExpception e) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Could not find product with category " + category
			);
		}
	}

}
