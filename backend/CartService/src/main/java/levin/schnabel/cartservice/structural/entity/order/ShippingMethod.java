package levin.schnabel.cartservice.structural.entity.order;

public enum ShippingMethod {

	STANDARD("standard"),
	NEXT_DAY("nextDay");

	private final String name;

	ShippingMethod(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
