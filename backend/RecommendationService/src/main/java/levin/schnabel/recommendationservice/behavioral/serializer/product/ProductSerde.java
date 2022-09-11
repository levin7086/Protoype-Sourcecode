package levin.schnabel.recommendationservice.behavioral.serializer.product;

import levin.schnabel.recommendationservice.structural.entity.Product;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class ProductSerde implements Serde<Product> {
	@Override
	public Serializer<Product> serializer() {
		return new ProductSerializer();
	}

	@Override
	public Deserializer<Product> deserializer() {
		return new ProductDeserializer();
	}
}
