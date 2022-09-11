package levin.schnabel.catalogService.behavioral.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.catalogService.structural.entity.Product;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.List;

public class ProductListDeserializer implements Deserializer<List<Product>> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<Product> deserialize(String topic, byte[] data) {
		try {
			if (data == null)
				return null;
			System.out.println("Deserializing Product");
			return objectMapper.readValue(new String(data, "UTF-8"), (Class<List<Product>>) ((Class) List.class));
		} catch (Exception e) {
			throw new SerializationException("Error deserializing to Product");
		}
	}
}
