package levin.schnabel.orderservice.config;

import levin.schnabel.orderservice.structural.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KTopicConfiguration {

	@Bean
	public NewTopic orderCreatedTopic() {
		return TopicBuilder.name(Constants.ORDER_CREATED_TOPIC).build();
	}


}
