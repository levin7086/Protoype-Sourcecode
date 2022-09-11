package levin.schnabel.orderservice.behavior.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.orderservice.structural.entity.Order;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class OrderDeserializer implements Deserializer<Order> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Order deserialize(String topic, byte[] data) {
		try {
			if (data == null)
				return null;
			System.out.println("Deserializing Order");
			return objectMapper.readValue(new String(data, "UTF-8"), Order.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SerializationException("Error deserializing to Order");
		}
	}
}
