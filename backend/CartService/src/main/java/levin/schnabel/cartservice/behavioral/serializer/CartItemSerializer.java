package levin.schnabel.cartservice.behavioral.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.cartservice.structural.entity.CartItem;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class CartItemSerializer implements Serializer<CartItem> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, CartItem cartItem) {

		if (cartItem == null)
			return null;

		try {
			System.out.println("Serializing Product");
			return objectMapper.writeValueAsBytes(cartItem);
		} catch (Exception e) {
			throw new SerializationException("Error serializing Product");
		}
	}
}
