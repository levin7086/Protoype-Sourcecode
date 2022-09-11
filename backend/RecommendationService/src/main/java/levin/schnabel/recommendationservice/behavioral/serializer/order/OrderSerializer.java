package levin.schnabel.recommendationservice.behavioral.serializer.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.recommendationservice.structural.entity.order.Order;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class OrderSerializer implements Serializer<Order> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, Order order) {

		if (order == null)
			return null;

		try {
			System.out.println("Serializing Order");
			return objectMapper.writeValueAsBytes(order);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SerializationException("Error serializing Order");
		}
	}
}
