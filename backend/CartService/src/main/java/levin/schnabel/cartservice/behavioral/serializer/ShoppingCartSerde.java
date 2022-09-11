package levin.schnabel.cartservice.behavioral.serializer;

import levin.schnabel.cartservice.structural.entity.ShoppingCart;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class ShoppingCartSerde implements Serde<ShoppingCart> {
	@Override
	public Serializer<ShoppingCart> serializer() {
		return new ShoppingCartSerializer();
	}

	@Override
	public Deserializer<ShoppingCart> deserializer() {
		return new ShoppingCartDeserializer();
	}
}
