package levin.schnabel.recommendationservice.behavioral.serializer.recommendation;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.recommendationservice.structural.entity.RecommendationScore;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class RecommendationScoreSerializer implements Serializer<RecommendationScore> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, RecommendationScore recommendationScore) {

		if (recommendationScore == null)
			return null;

		try {
			System.out.println("Serializing RecommendationScore");
			return objectMapper.writeValueAsBytes(recommendationScore);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SerializationException("Error serializing RecommendationScore");
		}
	}
}
