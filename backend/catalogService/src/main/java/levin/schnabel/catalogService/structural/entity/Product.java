package levin.schnabel.catalogService.structural.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"articleNumber", "name", "description", "category", "currentAssortment", "price"})
public class Product {

	private String articleNumber;
	private String name;
	private Double price;
	private String description;
	private String category;
	private boolean currentAssortment;

	public String getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(String articleNumber) {
		this.articleNumber = articleNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCurrentAssortment() {
		return currentAssortment;
	}

	public void setCurrentAssortment(boolean currentAssortment) {
		this.currentAssortment = currentAssortment;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}