package levin.schnabel.recommendationservice.behavioral.serializer.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.recommendationservice.structural.entity.Product;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class ProductDeserializer implements Deserializer<Product> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Product deserialize(String topic, byte[] data) {
		try {
			if (data == null)
				return null;
			System.out.println("Deserializing Product");
			return objectMapper.readValue(new String(data, "UTF-8"), Product.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SerializationException("Error deserializing to Product");
		}
	}
}
