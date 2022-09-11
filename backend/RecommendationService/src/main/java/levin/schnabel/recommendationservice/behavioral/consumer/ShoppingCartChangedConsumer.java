package levin.schnabel.recommendationservice.behavioral.consumer;

import levin.schnabel.recommendationservice.behavioral.service.CategoryScoreService;
import levin.schnabel.recommendationservice.structural.Constants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartChangedConsumer {

	private final CategoryScoreService categoryScoreService;

	public ShoppingCartChangedConsumer(CategoryScoreService categoryScoreService) {
		this.categoryScoreService = categoryScoreService;
	}

	@KafkaListener(containerFactory = "cartKafkaListenerContainerFactory", groupId = "recommendation-cart-consumer-group", topics = Constants.CART_ITEM_CHANGED_TOPIC)
	public void listen(@Payload String productId,
	                   @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String userId) throws Exception {
		categoryScoreService.updateCategoryScoreByShoppingCart(productId, userId);
	}

}
