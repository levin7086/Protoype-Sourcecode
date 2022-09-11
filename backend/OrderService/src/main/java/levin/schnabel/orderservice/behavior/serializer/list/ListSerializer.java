package levin.schnabel.orderservice.behavior.serializer.list;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.orderservice.structural.entity.Order;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.List;

public class ListSerializer implements Serializer<List<Order>> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, List<Order> orderList) {

		if (orderList == null)
			return null;

		try {
			System.out.println("Serializing OrderList");
			return objectMapper.writeValueAsBytes(orderList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SerializationException("Error serializing OrderList");
		}
	}
}
