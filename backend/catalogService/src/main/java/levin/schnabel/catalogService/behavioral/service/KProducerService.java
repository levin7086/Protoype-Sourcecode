package levin.schnabel.catalogService.behavioral.service;

import levin.schnabel.catalogService.structural.Constants;
import levin.schnabel.catalogService.structural.entity.Product;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KProducerService {

	KafkaTemplate<String, Product> kTemplate;

	KProducerService(KafkaTemplate<String, Product> kTemplate) {
		this.kTemplate = kTemplate;
	}

	public void produceProductCreation(Product product) {
		kTemplate.send(Constants.productCreatedTopic, product.getArticleNumber(), product);
	}

	public void produceProdcutUpdate(Product product) {
		kTemplate.send(Constants.productUpdatedTopic, product.getArticleNumber(), product);
	}
}
