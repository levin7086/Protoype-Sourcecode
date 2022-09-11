package levin.schnabel.recommendationservice.behavioral.serializer.recommendation;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.recommendationservice.structural.entity.RecommendationScore;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class RecommendationScoreDeserializer implements Deserializer<RecommendationScore> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public RecommendationScore deserialize(String topic, byte[] data) {
		try {
			if (data == null)
				return null;
			System.out.println("Deserializing RecommendationScore");
			return objectMapper.readValue(new String(data, "UTF-8"), RecommendationScore.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SerializationException("Error deserializing to RecommendationScore");
		}
	}
}
