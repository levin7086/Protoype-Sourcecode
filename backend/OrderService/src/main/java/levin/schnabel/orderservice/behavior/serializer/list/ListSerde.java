package levin.schnabel.orderservice.behavior.serializer.list;

import levin.schnabel.orderservice.structural.entity.Order;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.List;

public class ListSerde implements Serde<List<Order>> {
	@Override
	public Serializer<List<Order>> serializer() {
		return new ListSerializer();
	}

	@Override
	public Deserializer<List<Order>> deserializer() {
		return new ListDeserializer();
	}
}
