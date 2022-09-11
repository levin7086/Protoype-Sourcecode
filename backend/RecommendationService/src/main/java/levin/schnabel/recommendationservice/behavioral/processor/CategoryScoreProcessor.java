package levin.schnabel.recommendationservice.behavioral.processor;

import levin.schnabel.recommendationservice.behavioral.serializer.recommendation.RecommendationScoreSerde;
import levin.schnabel.recommendationservice.structural.Constants;
import levin.schnabel.recommendationservice.structural.entity.RecommendationScore;
import levin.schnabel.recommendationservice.structural.entity.RecommendationType;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryScoreProcessor {

	public static void process(KStream<String, RecommendationScore> categoryScoreStream) {
		Serde<RecommendationScore> scoreSerde = new RecommendationScoreSerde();
		Serde<List<RecommendationScore>> scoreListSerde = new Serdes.ListSerde<>(ArrayList.class, scoreSerde);

		categoryScoreStream
				.filter((key, value) -> value.getType() == RecommendationType.PRODUCT_CATEGORY)
				.groupByKey(Grouped.with(Serdes.String(), scoreSerde))
				.aggregate(
						ArrayList::new,
						(key, score, aggregate) -> {
							RecommendationScore existing = new RecommendationScore(RecommendationType.PRODUCT_CATEGORY);
							existing.setRecommendedIdentifier(score.getRecommendedIdentifier());
							Optional<RecommendationScore> optExisting =
									aggregate.stream()
											.filter(recommendationScore -> recommendationScore
													.getRecommendedIdentifier()
													.equals(score.getRecommendedIdentifier()))
											.findFirst();
							if (optExisting.isPresent()) {
								existing = optExisting.get();
								aggregate.remove(existing);
							}
							existing.setPoints(existing.getPoints() + score.getPoints());
							aggregate.add(existing);
							return aggregate;
						},
						Materialized.<String, List<RecommendationScore>, KeyValueStore<Bytes, byte[]>>as(Constants.CATEGORY_SCORE_STORE)
								.withKeySerde(Serdes.String())
								.withValueSerde(scoreListSerde)
				);
	}

}
