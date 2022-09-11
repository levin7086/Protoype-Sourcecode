package levin.schnabel.cartservice.structural.entity;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

	private Map<String, Integer> productIdAmountMap;

	public ShoppingCart() {
		productIdAmountMap = new HashMap<>();
	}

	public Map<String, Integer> getProductIdAmountMap() {
		return productIdAmountMap;
	}

	public void setProductIdAmountMap(Map<String, Integer> productIdAmountMap) {
		this.productIdAmountMap = productIdAmountMap;
	}

	public void clear() {
		productIdAmountMap.clear();
	}

}
