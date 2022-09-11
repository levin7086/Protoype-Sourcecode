package levin.schnabel.cartservice.config;

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
public class KProducerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public ProducerFactory<String, String> getProducerFactory() {
		return new DefaultKafkaProducerFactory<>(
				Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
						KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
						VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
				)
		);
	}

	@Bean
	public KafkaTemplate<String, String> getKafkaTemplate(ProducerFactory<String, String> producerFactory) {
		return new KafkaTemplate<String, String>(producerFactory);
	}

}
