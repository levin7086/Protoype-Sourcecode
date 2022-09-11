package levin.schnabel.cartservice.behavioral.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.cartservice.structural.entity.ShoppingCart;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class ShoppingCartSerializer implements Serializer<ShoppingCart> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, ShoppingCart shoppingCart) {

		if (shoppingCart == null)
			return null;

		try {
			System.out.println("Serializing Product");
			return objectMapper.writeValueAsBytes(shoppingCart);
		} catch (Exception e) {
			throw new SerializationException("Error serializing Product");
		}
	}
}
