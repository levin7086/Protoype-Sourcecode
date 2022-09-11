package levin.schnabel.catalogService.config;

import levin.schnabel.catalogService.structural.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KTopicConfiguration {

	@Bean
	public NewTopic productCreateTopic() {
		return TopicBuilder.name(Constants.productCreatedTopic).build();
	}

	@Bean
	public NewTopic productUpdateTopic() {
		return TopicBuilder.name(Constants.productUpdatedTopic).build();
	}

	@Bean
	public NewTopic productCatalogTopic() {
		return TopicBuilder.name(Constants.productCatalogTopic).build();
	}


	@Bean
	public NewTopic productIdPriceTopic() {
		return TopicBuilder.name(Constants.PRODUCTID_PRICE_TOPIC).build();
	}

}
