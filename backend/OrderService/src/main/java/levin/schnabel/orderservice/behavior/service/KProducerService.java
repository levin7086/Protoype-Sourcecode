package levin.schnabel.orderservice.behavior.service;

import levin.schnabel.orderservice.structural.Constants;
import levin.schnabel.orderservice.structural.entity.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KProducerService {

	KafkaTemplate<String, Order> kTemplate;

	KProducerService(KafkaTemplate<String, Order> kTemplate) {
		this.kTemplate = kTemplate;
	}

	public void produceOrderCreation(Order order) {
		kTemplate.send(Constants.ORDER_CREATED_TOPIC, order.getOrderID(), order);
	}
}
