package levin.schnabel.recommendationservice.behavioral.consumer;

import levin.schnabel.recommendationservice.behavioral.service.CategoryScoreService;
import levin.schnabel.recommendationservice.structural.Constants;
import levin.schnabel.recommendationservice.structural.entity.order.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderReceivedConsumer {

	private CategoryScoreService categoryScoreService;

	public OrderReceivedConsumer(CategoryScoreService categoryScoreService) {
		this.categoryScoreService = categoryScoreService;
	}

	@KafkaListener(groupId = "recommendation-consumer-group", topics = Constants.ORDER_CREATED_TOPIC)
	public void listen(Order order) {
		categoryScoreService.updateCategoryScoreByOrder(order);
	}


}
