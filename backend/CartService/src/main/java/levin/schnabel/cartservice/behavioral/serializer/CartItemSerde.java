package levin.schnabel.cartservice.behavioral.serializer;

import levin.schnabel.cartservice.structural.entity.CartItem;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class CartItemSerde implements Serde<CartItem> {
	@Override
	public Serializer<CartItem> serializer() {
		return new CartItemSerializer();
	}

	@Override
	public Deserializer<CartItem> deserializer() {
		return new CartItemDeserializer();
	}
}
