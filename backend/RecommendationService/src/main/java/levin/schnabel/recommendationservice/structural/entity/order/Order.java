package levin.schnabel.recommendationservice.structural.entity.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

	private String userId;
	private List<OrderPosition> orderPositionList;
	private String orderID;
	private OrderState orderState;
	private Date creationDate;
	private Address shippingAddress;
	private Address billingAddress;
	private ShippingMethod shippingMethod;
	private PaymentMethod paymentMethod;

	public Order() {
		orderPositionList = new ArrayList<OrderPosition>();
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public OrderState getOrderState() {
		return orderState;
	}

	public void setOrderState(OrderState orderState) {
		this.orderState = orderState;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public void addPosition(OrderPosition position) {
		orderPositionList.add(position);
	}

	public void removePosition(OrderPosition position) {
		orderPositionList.remove(position);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<OrderPosition> getOrderPositionList() {
		return orderPositionList;
	}

	public void setOrderPositionList(List<OrderPosition> orderPositionList) {
		this.orderPositionList = orderPositionList;
	}

	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
