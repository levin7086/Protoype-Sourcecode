package levin.schnabel.catalogService.structural.entity;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Category {

	SPORTS(null),
	COMPETITIVE(SPORTS),
	VOLLEYBALL(COMPETITIVE),
	BEACH_VOLLEYBALL(VOLLEYBALL),
	INDOOR_VOLLEYBALL(VOLLEYBALL),
	TABLE_TENNIS(COMPETITIVE),
	TABLE_TENNIS_RACKETS(TABLE_TENNIS),
	TABLE_TENNIS_EQUIPMENT(TABLE_TENNIS),

	CASUAL(SPORTS),
	ROPE_SKIPPING(CASUAL),
	RUNNING(CASUAL),
	SHOES(RUNNING),
	RUNNING_TRACKERS(RUNNING),
	CYCLING(CASUAL),
	BIKES(CYCLING),
	BIKE_COMPONENTS(CYCLING),
	HELMETS(CYCLING);


	private final Category parent;

	Category(Category parent) {
		this.parent = parent;
	}

	public Category getParent() {
		return parent;
	}

	public String getName() {
		return this.name();
	}

	public Set<Category> getChildren() {
		return Arrays.stream(Category.values()).filter(category -> category.parent == this).collect(Collectors.toSet());
	}

	public Set<Category> getSiblings() {
		return Arrays.stream(Category.values()).filter(category -> category != this && category.parent == this.parent).collect(Collectors.toSet());
	}

}
