package levin.schnabel.cartservice.behavioral.controller;

import levin.schnabel.cartservice.behavioral.service.CartService;
import levin.schnabel.cartservice.structural.entity.CartItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetCartController {

	private final CartService cartService;

	public GetCartController(CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping("/cart/{userId}")
	public List<CartItem> getCart(@PathVariable String userId) throws Exception {
		return cartService.getCart(userId);
	}

}
