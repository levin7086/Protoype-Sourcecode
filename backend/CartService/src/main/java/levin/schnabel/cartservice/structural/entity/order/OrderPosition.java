package levin.schnabel.cartservice.structural.entity.order;

public class OrderPosition {

	String articleNumber;
	double price;
	int quantity;

	public String getArticleNumber() {
		return articleNumber;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}
}
