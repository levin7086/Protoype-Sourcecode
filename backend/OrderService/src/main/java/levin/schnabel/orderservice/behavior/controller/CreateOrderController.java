package levin.schnabel.orderservice.behavior.controller;

import levin.schnabel.orderservice.behavior.service.OrderService;
import levin.schnabel.orderservice.structural.entity.OrderDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateOrderController {

	private final OrderService orderService;

	public CreateOrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping(
			path = "order/create",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> create(@RequestBody OrderDTO order) {
		orderService.createOrder(order);
		return ResponseEntity.ok("Order created");
	}

}
