package levin.schnabel.recommendationservice.structural.entity;

public class ComplementaryRecommendation extends AbstractRecommendation {

	public RecommendationType recommendationType = RecommendationType.SHOPPING_CART;

	public ComplementaryRecommendation() {
		super.setRecommendationType(recommendationType);
	}

}
