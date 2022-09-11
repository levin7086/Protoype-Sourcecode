package levin.schnabel.recommendationservice.behavioral.serializer.recommendation;

import levin.schnabel.recommendationservice.structural.entity.RecommendationScore;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class RecommendationScoreSerde implements Serde<RecommendationScore> {
	@Override
	public Serializer<RecommendationScore> serializer() {
		return new RecommendationScoreSerializer();
	}

	@Override
	public Deserializer<RecommendationScore> deserializer() {
		return new RecommendationScoreDeserializer();
	}
}
