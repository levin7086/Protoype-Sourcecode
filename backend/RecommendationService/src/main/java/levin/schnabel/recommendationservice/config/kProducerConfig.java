package levin.schnabel.recommendationservice.config;

import levin.schnabel.recommendationservice.behavioral.serializer.recommendation.RecommendationScoreSerializer;
import levin.schnabel.recommendationservice.structural.entity.RecommendationScore;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@Configuration
public class kProducerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public ProducerFactory<String, RecommendationScore> getProducerFactory() {
		return new DefaultKafkaProducerFactory<>(
				Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
						KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
						VALUE_SERIALIZER_CLASS_CONFIG, RecommendationScoreSerializer.class
				)
		);
	}

	@Bean
	public KafkaTemplate<String, RecommendationScore> getKafkaTemplate(ProducerFactory<String, RecommendationScore> producerFactory) {
		return new KafkaTemplate<String, RecommendationScore>(producerFactory);
	}

}
