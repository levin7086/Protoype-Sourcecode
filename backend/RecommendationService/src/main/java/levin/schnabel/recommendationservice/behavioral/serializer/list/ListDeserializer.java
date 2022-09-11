package levin.schnabel.recommendationservice.behavioral.serializer.list;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.recommendationservice.structural.entity.RecommendationScore;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.List;

public class ListDeserializer implements Deserializer<List<RecommendationScore>> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<RecommendationScore> deserialize(String topic, byte[] data) {
		try {
			if (data == null)
				return null;
			System.out.println("Deserializing RecommendationScoreList");
			return objectMapper.readValue(new String(data, "UTF-8"), (Class<List<RecommendationScore>>) ((Class) List.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new SerializationException("Error deserializing to RecommendationScoreList");
		}
	}
}
