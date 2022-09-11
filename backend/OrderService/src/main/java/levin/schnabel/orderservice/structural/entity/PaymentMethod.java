package levin.schnabel.orderservice.structural.entity;

public enum PaymentMethod {

	SECURE_PAY("securePay"),
	CREDIT_CARD("creditCard");

	private final String name;

	PaymentMethod(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
