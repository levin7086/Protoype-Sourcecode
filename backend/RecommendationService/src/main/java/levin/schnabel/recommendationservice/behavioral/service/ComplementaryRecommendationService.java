package levin.schnabel.recommendationservice.behavioral.service;

import levin.schnabel.recommendationservice.structural.Constants;
import levin.schnabel.recommendationservice.structural.entity.ComplementaryRecommendation;
import levin.schnabel.recommendationservice.structural.entity.Product;
import levin.schnabel.recommendationservice.structural.entity.RecommendationScore;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComplementaryRecommendationService {

	private final CategoryScoreService categoryScoreService;
	private final StreamsBuilderFactoryBean factoryBean;

	public ComplementaryRecommendationService(CategoryScoreService categoryScoreService, StreamsBuilderFactoryBean factoryBean) {
		this.categoryScoreService = categoryScoreService;
		this.factoryBean = factoryBean;
	}

	public ComplementaryRecommendation getComplementaryRecommendation(String userId, List<String> productIdList) throws Exception {
		ComplementaryRecommendation recommendation = new ComplementaryRecommendation();
		Set<String> includedCategories = productIdList.stream().map(id -> {
			try {
				return categoryScoreService.getCategory(id);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}).collect(Collectors.toSet());
		List<RecommendationScore> recommendedCategories = categoryScoreService.getCategoryScores(userId);
		includedCategories = includedCategories.stream().sorted((o1, o2) -> {
			Optional<RecommendationScore> score1 = recommendedCategories.stream().filter(score -> score.getRecommendedIdentifier().equals(o1)).findFirst();
			Optional<RecommendationScore> score2 = recommendedCategories.stream().filter(score -> score.getRecommendedIdentifier().equals(o1)).findFirst();

			if (score1.isEmpty()) {
				return 0;
			}
			if (score2.isEmpty()) {
				return 0;
			}

			return score1.get().getPoints() - score2.get().getPoints();
		}).limit(3).collect(Collectors.toCollection(LinkedHashSet::new));

		includedCategories.forEach(s -> {
			List<Product> list = getProductsByCategory(s)
					.stream()
					.filter(product -> !productIdList.contains(product.getArticleNumber()))
					.collect(Collectors.toList());
			Collections.shuffle(list);
			list.stream().limit(2).forEach(product -> recommendation.addRecommendation(product.getArticleNumber()));
		});
		return recommendation;
	}

	private List<Product> getProductsByCategory(String categoryName) {
		try {
			final KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
			final ReadOnlyKeyValueStore<String, List<Product>> store = kafkaStreams
					.store(StoreQueryParameters.fromNameAndType
							(
									Constants.PRODUCT_CATALOG_BY_CATEGORY_STORE,
									QueryableStoreTypes.keyValueStore()
							));
			if (store.get(categoryName) != null)
				return store.get(categoryName);
			else
				throw new RuntimeException("Could not identify product by id");
		} catch (Exception e) {
			throw new RuntimeException("Retrieving Product failed");
		}
	}
}
