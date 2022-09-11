package levin.schnabel.orderservice.structural.entity;

public enum ShippingMethod {

	STANDARD("standard", 3.00),
	NEXT_DAY("nextDay", 8.00);

	private final String name;
	private final double price;

	ShippingMethod(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}
}
