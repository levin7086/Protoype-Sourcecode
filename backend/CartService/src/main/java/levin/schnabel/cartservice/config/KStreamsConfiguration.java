package levin.schnabel.cartservice.config;

import levin.schnabel.cartservice.behavioral.serializer.ShoppingCartSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.Map;

@Configuration
public class KStreamsConfiguration {

	@Value("${spring.kafka.streams.application-id}")
	private String applicationId;

	@Bean(name =
			KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public KafkaStreamsConfiguration kStreamConfig() {
		return new KafkaStreamsConfiguration(
				Map.of(
						StreamsConfig.APPLICATION_ID_CONFIG, applicationId,
						StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
						StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class,
						StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, ShoppingCartSerde.class,
						StreamsConfig.STATE_DIR_CONFIG, "../kafka-streams/cart-service",
						ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
				));
	}

}
