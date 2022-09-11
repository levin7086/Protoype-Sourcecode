package levin.schnabel.orderservice.behavior.controller;

import levin.schnabel.orderservice.behavior.service.OrderService;
import levin.schnabel.orderservice.structural.entity.OrderDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetOrdersController {

	private final OrderService orderService;

	public GetOrdersController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/orders/{userId}")
	public List<OrderDTO> getOrders(@PathVariable String userId) {
		return this.orderService.getOrders(userId);
	}

}
