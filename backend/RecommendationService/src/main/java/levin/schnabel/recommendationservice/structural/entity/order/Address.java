package levin.schnabel.recommendationservice.structural.entity.order;

public class Address {

	private String firstName;
	private String lastName;
	private String street;
	private String houseNumber;
	private String postalCode;
	private String place;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getPlace() {
		return place;
	}

	public String getStreet() {
		return street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}
}
