package levin.schnabel.cartservice.behavioral.serializer.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.cartservice.structural.entity.order.Order;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class OrderDeserializer implements Deserializer<Order> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Order deserialize(String topic, byte[] data) {
		try {
			if (data == null)
				return null;
			System.out.println("Deserializing Product");
			return objectMapper.readValue(new String(data, "UTF-8"), Order.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SerializationException("Error deserializing to Product");
		}
	}
}
