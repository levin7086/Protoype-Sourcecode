package levin.schnabel.recommendationservice.structural.entity.order;

public class OrderPosition {

	String articleNumber;
	double price;
	int quantity;

	public OrderPosition() {
	}

	public OrderPosition(String articleNumber, double price, int quantity) {
		this.articleNumber = articleNumber;
		this.price = price;
		this.quantity = quantity;
	}

	public String getArticleNumber() {
		return articleNumber;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setArticleNumber(String articleNumber) {
		this.articleNumber = articleNumber;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
