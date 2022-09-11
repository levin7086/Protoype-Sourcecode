package levin.schnabel.orderservice.structural.entity;

public class OrderDTO {
	private String userId;
	private String orderNumber;
	private Address shippingAddress;
	private Address billingAddress;
	private OrderPosition[] orderPositions;
	private String paymentMethod;
	private String shippingMethod;
	private String orderStatus;
	private double totalPrice;

	public String getUserId() {
		return userId;
	}

	public static OrderDTO fromOrder(Order order) {
		OrderDTO dto = new OrderDTO();
		dto.userId = order.getUserId();
		dto.orderNumber = order.getOrderID();
		dto.shippingAddress = order.getShippingAddress();
		dto.billingAddress = order.getBillingAddress();
		dto.orderPositions = order.getOrderPositionList().toArray(OrderPosition[]::new);
		dto.paymentMethod = order.getPaymentMethod().getName();
		dto.shippingMethod = order.getShippingMethod().getName();
		dto.orderStatus = order.getOrderState().toString();
		dto.totalPrice = order.getOrderPositionList().stream().reduce(0d, (sum, value) -> sum + value.getPrice(), Double::sum);
		return dto;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public OrderPosition[] getOrderPositions() {
		return orderPositions;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public double getTotalPrice() {
		return totalPrice;
	}
}
