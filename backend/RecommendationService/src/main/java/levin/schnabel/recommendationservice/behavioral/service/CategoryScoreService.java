package levin.schnabel.recommendationservice.behavioral.service;

import levin.schnabel.recommendationservice.structural.Constants;
import levin.schnabel.recommendationservice.structural.entity.AbstractRecommendation;
import levin.schnabel.recommendationservice.structural.entity.RecommendationScore;
import levin.schnabel.recommendationservice.structural.entity.RecommendationType;
import levin.schnabel.recommendationservice.structural.entity.order.CategoryRecommendation;
import levin.schnabel.recommendationservice.structural.entity.order.Order;
import levin.schnabel.recommendationservice.structural.entity.order.OrderPosition;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryScoreService {

	private final KafkaTemplate<String, RecommendationScore> kafkaTemplate;
	private final StreamsBuilderFactoryBean factoryBean;

	public CategoryScoreService(KafkaTemplate<String, RecommendationScore> kafkaTemplate, StreamsBuilderFactoryBean factoryBean) {
		this.kafkaTemplate = kafkaTemplate;
		this.factoryBean = factoryBean;
	}

	public void updateCategoryScoreByShoppingCart(String productId, String userId) throws Exception {
		RecommendationScore score = new RecommendationScore(RecommendationType.PRODUCT_CATEGORY);
		score.setRecommendedIdentifier(productId);
		score.setRecommendedIdentifier(getCategory(productId));
		if (score.getRecommendedIdentifier() == null) return;
		kafkaTemplate.send(
				Constants.RECOMMENDATION_SCORE_CHANGED,
				userId,
				score
		);
	}

	public void updateCategoryScoreByOrder(Order order) {
		order.getOrderPositionList()
				.stream()
				.map(OrderPosition::getArticleNumber)
				.forEach(articleNumber -> {
					RecommendationScore score = new RecommendationScore(RecommendationType.PRODUCT_CATEGORY);
					try {
						score.setRecommendedIdentifier(getCategory(articleNumber));
						if (score.getRecommendedIdentifier() == null) return;
						score.setPoints(RecommendationScore.CATEGORY_ORDER_POINTS);
						kafkaTemplate.send(
								Constants.RECOMMENDATION_SCORE_CHANGED,
								order.getUserId(),
								score
						);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}

	public AbstractRecommendation getCategoryRecommendation(String userId) throws Exception {
		List<RecommendationScore> scoreList = getCategoryScores(userId);
		AbstractRecommendation recommendation = new CategoryRecommendation();
		scoreList.stream().sorted(Comparator.comparingInt(RecommendationScore::getPoints))
				.forEachOrdered(recommendationScore ->
						{
							recommendation.addRecommendation(recommendationScore.getRecommendedIdentifier());
						}
				);
		recommendation.setRecommendationList
				(
						recommendation.getRecommendationList().stream().collect(reverse()).collect(Collectors.toList())
				);
		return recommendation;
	}

	protected List<RecommendationScore> getCategoryScores(String userId) throws Exception {
		List<RecommendationScore> recommendationScoreList = new ArrayList<>();
		try {
			final KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
			final ReadOnlyKeyValueStore<String, List<RecommendationScore>> store = kafkaStreams
					.store(StoreQueryParameters.fromNameAndType
							(
									Constants.CATEGORY_SCORE_STORE,
									QueryableStoreTypes.keyValueStore()
							));
			if (store.get(userId) != null) {
				recommendationScoreList = store.get(userId);
			}
			return recommendationScoreList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Retrieving CategoryScore from Store failed");
		}
	}

	public String getCategory(String articleNumber) throws Exception {
		String category;
		try {
			final KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
			final ReadOnlyKeyValueStore<String, String> store = kafkaStreams
					.store(StoreQueryParameters.fromNameAndType
							(
									Constants.CATEGORY_BY_PRODUCTID_STORE,
									QueryableStoreTypes.keyValueStore()
							));
			if (store.get(articleNumber) != null) {
				category = store.get(articleNumber);
				return category;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Retrieving Category from Store failed");
		}
	}

	private static <T> Collector<T, ?, Stream<T>> reverse() {
		return Collectors.collectingAndThen(Collectors.toList(), list -> {
			Collections.reverse(list);
			return list.stream();
		});
	}


}
