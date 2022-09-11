package levin.schnabel.recommendationservice.behavioral.serializer.list;

import levin.schnabel.recommendationservice.structural.entity.RecommendationScore;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.List;

public class ListSerde implements Serde<List<RecommendationScore>> {
	@Override
	public Serializer<List<RecommendationScore>> serializer() {
		return new ListSerializer();
	}

	@Override
	public Deserializer<List<RecommendationScore>> deserializer() {
		return new ListDeserializer();
	}
}
