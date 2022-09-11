package levin.schnabel.cartservice.behavioral.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.cartservice.structural.entity.ShoppingCart;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class ShoppingCartDeserializer implements Deserializer<ShoppingCart> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ShoppingCart deserialize(String topic, byte[] data) {
		try {
			if (data == null)
				return null;
			return objectMapper.readValue(new String(data, "UTF-8"), ShoppingCart.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SerializationException("Error deserializing to Product");
		}
	}
}
