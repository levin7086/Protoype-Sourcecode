package levin.schnabel.cartservice.behavioral.processor;

import levin.schnabel.cartservice.behavioral.service.CartService;
import levin.schnabel.cartservice.structural.Constants;
import levin.schnabel.cartservice.structural.entity.order.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

	private CartService cartService;

	public OrderConsumer(CartService cartService) {
		this.cartService = cartService;
	}

	@KafkaListener(groupId = "cart-consumer-group", topics = Constants.ORDER_CREATED_TOPIC)
	public void listen(Order order) {
		cartService.clearCart(order.getUserId());
	}

}
