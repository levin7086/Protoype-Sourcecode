package levin.schnabel.recommendationservice.config;

import levin.schnabel.recommendationservice.behavioral.serializer.order.OrderDeserializer;
import levin.schnabel.recommendationservice.structural.entity.order.Order;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KConsumerConfiguration {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	@Value("${spring.kafka.consumer.group-id}")
	private String consumerGroup;

	@Bean
	public ConsumerFactory<String, Order> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				bootstrapServers);
		props.put(
				ConsumerConfig.GROUP_ID_CONFIG,
				consumerGroup);
		props.put(
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class);
		props.put(
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				OrderDeserializer.class);
		props.put(
				ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
				"earliest");
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Order> kafkaListenerContainerFactory(
			ConsumerFactory<String, Order> consumerFactory
	) {
		ConcurrentKafkaListenerContainerFactory<String, Order> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}

}
