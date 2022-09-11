package levin.schnabel.cartservice.behavioral.controller;

import levin.schnabel.cartservice.behavioral.service.CartService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddToCartController {

	private final CartService cartService;

	public AddToCartController(CartService cartService) {
		this.cartService = cartService;
	}

	@PostMapping
			(
					path = "cart/add",
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public void addToCart(@RequestParam String userId, @RequestParam String productId) {
		cartService.addProductToCart(userId, productId);
//		return ResponseEntity.ok().build();
	}

}
