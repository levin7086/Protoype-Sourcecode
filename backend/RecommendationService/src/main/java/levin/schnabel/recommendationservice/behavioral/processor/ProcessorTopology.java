package levin.schnabel.recommendationservice.behavioral.processor;

import levin.schnabel.recommendationservice.behavioral.serializer.product.ProductSerde;
import levin.schnabel.recommendationservice.behavioral.serializer.recommendation.RecommendationScoreSerde;
import levin.schnabel.recommendationservice.structural.Constants;
import levin.schnabel.recommendationservice.structural.entity.Product;
import levin.schnabel.recommendationservice.structural.entity.RecommendationScore;
import levin.schnabel.recommendationservice.structural.entity.RecommendationType;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessorTopology {

	@Bean
	public KStream<String, Product> kStream(StreamsBuilder kStreamBuilder) {

		Serde<Product> productSerde = new ProductSerde();
		Serde<RecommendationScore> scoreSerde = new RecommendationScoreSerde();

		KStream<String, Product> productStream = kStreamBuilder.stream(Constants.PRODUCT_CATALOG_TOPIC,
				Consumed.with(Serdes.String(), productSerde));
		KStream<String, RecommendationScore> categoryScoreStream = kStreamBuilder
				.stream(Constants.RECOMMENDATION_SCORE_CHANGED, Consumed.with(Serdes.String(), scoreSerde))
				.filter((key, value) -> value.getType() == RecommendationType.PRODUCT_CATEGORY);

		ProductProcessor.process(productStream);
		CategoryScoreProcessor.process(categoryScoreStream);

		return null;
	}

}
