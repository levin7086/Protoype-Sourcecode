package levin.schnabel.recommendationservice.behavioral.serializer.list;

import com.fasterxml.jackson.databind.ObjectMapper;
import levin.schnabel.recommendationservice.structural.entity.RecommendationScore;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.List;

public class ListSerializer implements Serializer<List<RecommendationScore>> {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, List<RecommendationScore> recommendationScoreList) {

		if (recommendationScoreList == null)
			return null;

		try {
			System.out.println("Serializing RecommendationScoreList");
			return objectMapper.writeValueAsBytes(recommendationScoreList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SerializationException("Error serializing RecommendationScoreList");
		}
	}
}
