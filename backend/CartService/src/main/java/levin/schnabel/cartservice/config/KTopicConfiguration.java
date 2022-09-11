package levin.schnabel.cartservice.config;

import levin.schnabel.cartservice.structural.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KTopicConfiguration {

	@Bean
	public NewTopic cartItemChangedTopic() {
		return TopicBuilder.name(Constants.CART_ITEM_CHANGED_TOPIC).build();
	}

	@Bean
	public NewTopic userCartTopic() {
		return TopicBuilder.name(Constants.USER_CART_TOPIC).build();
	}
}
