package levin.schnabel.recommendationservice.structural.entity;

public class RecommendationScore {

	public static final int CATEGORY_ORDER_POINTS = 2;
	public static final int CATEGORY_CART_POINTS = 1;

	private RecommendationType type;
	private String recommendedIdentifier;
	private int points;

	public RecommendationScore() {
	}

	public RecommendationScore(RecommendationType recommendationType) {
		this.type = recommendationType;
	}

	public RecommendationType getType() {
		return type;
	}

	public void setType(RecommendationType type) {
		this.type = type;
	}

	public String getRecommendedIdentifier() {
		return recommendedIdentifier;
	}

	public void setRecommendedIdentifier(String recommendedIdentifier) {
		this.recommendedIdentifier = recommendedIdentifier;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
