package levin.schnabel.catalogService.behavioral.serializer;

import levin.schnabel.catalogService.structural.entity.Product;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.List;

public class ProductListSerde implements Serde<List<Product>> {
	@Override
	public Serializer<List<Product>> serializer() {
		return new ProductListSerializer();
	}

	@Override
	public Deserializer<List<Product>> deserializer() {
		return new ProductListDeserializer();
	}
}
