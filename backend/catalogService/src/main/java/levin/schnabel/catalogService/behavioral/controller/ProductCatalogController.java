package levin.schnabel.catalogService.behavioral.controller;

import levin.schnabel.catalogService.behavioral.service.ProductService;
import levin.schnabel.catalogService.structural.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

@RestController
public class ProductCatalogController {

	private final ProductService productService;

	@Autowired
	public ProductCatalogController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping(
			path = "catalog/create",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> create(@RequestBody Set<Product> productSet) {
		productService.createProducts(productSet);
		return ResponseEntity.ok("Products Created");
	}

	@GetMapping(path = "/catalog/generate")
	public void generate() throws IOException {
		productService.generateDummyCatalog();
	}

}
