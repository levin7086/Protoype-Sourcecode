package levin.schnabel.cartservice.structural.entity;

public class CartItem {

	private String productID;
	private int productQuantity;

	public CartItem() {
	}

	public CartItem(String productID, int productQuantity) {
		this.setProductID(productID);
		this.setProductQuantity(productQuantity);
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
}
