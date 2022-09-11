package levin.schnabel.cartservice.behavioral.serializer.order;

import levin.schnabel.cartservice.structural.entity.order.Order;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class OrderSerde implements Serde<Order> {
	@Override
	public Serializer<Order> serializer() {
		return new OrderSerializer();
	}

	@Override
	public Deserializer<Order> deserializer() {
		return new OrderDeserializer();
	}
}
