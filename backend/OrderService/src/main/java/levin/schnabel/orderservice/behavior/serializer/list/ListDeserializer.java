package levin.schnabel.orderservice.behavior.serializer.list;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.orderservice.structural.entity.Order;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.List;

public class ListDeserializer implements Deserializer<List<Order>> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<Order> deserialize(String topic, byte[] data) {
		try {
			if (data == null)
				return null;
			System.out.println("Deserializing OrderList");
			return objectMapper.readValue(new String(data, "UTF-8"), (Class<List<Order>>) ((Class) List.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new SerializationException("Error deserializing to OrderList");
		}
	}
}
