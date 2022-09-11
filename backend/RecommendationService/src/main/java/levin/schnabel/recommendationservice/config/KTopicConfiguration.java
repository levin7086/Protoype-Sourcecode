package levin.schnabel.recommendationservice.config;

import levin.schnabel.recommendationservice.structural.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KTopicConfiguration {

	@Bean
	public NewTopic categoryScoreTopic() {
		return TopicBuilder.name(Constants.RECOMMENDATION_SCORE_CHANGED).build();
	}


}
