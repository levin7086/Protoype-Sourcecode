package levin.schnabel.catalogService;


import levin.schnabel.catalogService.structural.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
public class CategoryTest {

	@Test
	void categoryHasCorrectParent() {
		assertSame(Category.INDOOR_VOLLEYBALL.getParent(), Category.VOLLEYBALL);
	}

	@Test
	void categoryHasCorrectChildren() {
		Assertions.assertTrue(Arrays.deepEquals(Category.VOLLEYBALL.getChildren().toArray(), new Category[]{Category.INDOOR_VOLLEYBALL, Category.BEACH_VOLLEYBALL}));

	}

	@Test
	void categoryHasCorrectSiblings() {
		Assertions.assertTrue(Arrays.deepEquals(Category.BIKES.getSiblings().toArray(), new Category[]{Category.HELMETS, Category.BIKE_COMPONENTS}));
	}

}
