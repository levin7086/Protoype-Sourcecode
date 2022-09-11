package levin.schnabel.recommendationservice.structural.entity.order;

import levin.schnabel.recommendationservice.structural.entity.AbstractRecommendation;
import levin.schnabel.recommendationservice.structural.entity.RecommendationType;

public class CategoryRecommendation extends AbstractRecommendation {

	public RecommendationType recommendationType = RecommendationType.PRODUCT_CATEGORY;

	public CategoryRecommendation() {
		super.setRecommendationType(recommendationType);
	}

}
