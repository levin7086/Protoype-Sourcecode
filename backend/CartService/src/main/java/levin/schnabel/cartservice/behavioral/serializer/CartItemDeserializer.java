package levin.schnabel.cartservice.behavioral.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.cartservice.structural.entity.CartItem;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class CartItemDeserializer implements Deserializer<CartItem> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public CartItem deserialize(String topic, byte[] data) {
		try {
			if (data == null)
				return null;
			System.out.println("Deserializing Product");
			return objectMapper.readValue(new String(data, "UTF-8"), CartItem.class);
		} catch (Exception e) {
			throw new SerializationException("Error deserializing to Product");
		}
	}
}
