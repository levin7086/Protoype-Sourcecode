package levin.schnabel.orderservice.behavior.serializer;

import levin.schnabel.orderservice.structural.entity.Order;
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
