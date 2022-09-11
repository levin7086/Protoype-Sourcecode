package levin.schnabel.recommendationservice.structural.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRecommendation {

	public RecommendationType recommendationType;

	private List<String> recommendationList;

	public AbstractRecommendation() {
		recommendationList = new ArrayList<>();
	}

	public List<String> getRecommendationList() {
		return recommendationList;
	}

	public void setRecommendationList(List<String> recommendationList) {
		this.recommendationList = recommendationList;
	}

	public void addRecommendation(String identifier) {
		recommendationList.add(identifier);
	}

	public RecommendationType getRecommendationType() {
		return recommendationType;
	}

	public void setRecommendationType(RecommendationType recommendationType) {
		this.recommendationType = recommendationType;
	}
}
