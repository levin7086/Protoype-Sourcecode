package levin.schnabel.recommendationservice.behavioral.controller;

import levin.schnabel.recommendationservice.behavioral.service.CategoryScoreService;
import levin.schnabel.recommendationservice.behavioral.service.ComplementaryRecommendationService;
import levin.schnabel.recommendationservice.structural.entity.ComplementaryRecommendation;
import levin.schnabel.recommendationservice.structural.entity.order.CategoryRecommendation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecommendationController {

	private final CategoryScoreService categoryScoreService;
	private final ComplementaryRecommendationService complementaryService;

	public RecommendationController(CategoryScoreService categoryScoreService, ComplementaryRecommendationService complementaryService) {
		this.categoryScoreService = categoryScoreService;
		this.complementaryService = complementaryService;
	}

	@PostMapping(
			path = "/recommendation/complementary/{userId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ComplementaryRecommendation getCartRecommendation(@PathVariable String userId, @RequestBody List<String> productIdList) throws Exception {
		return complementaryService.getComplementaryRecommendation(userId, productIdList);
	}

	@GetMapping("/recommendation/category/{userId}")
	public CategoryRecommendation getCategoryRecommendation(@PathVariable String userId) {
		try {
			return (CategoryRecommendation) categoryScoreService.getCategoryRecommendation(userId);
		} catch (Exception e) {
			return null;
		}
	}

}
