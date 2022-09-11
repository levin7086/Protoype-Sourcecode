package levin.schnabel.catalogService.behavioral.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.catalogService.structural.entity.Product;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class ProductSerializer implements Serializer<Product> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, Product product) {

		if (product == null)
			return null;

		try {
			System.out.println("Serializing Product");
			return objectMapper.writeValueAsBytes(product);
		} catch (Exception e) {
			throw new SerializationException("Error serializing Product");
		}
	}
}
