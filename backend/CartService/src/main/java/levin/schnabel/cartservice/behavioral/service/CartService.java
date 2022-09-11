package levin.schnabel.cartservice.behavioral.service;

import levin.schnabel.cartservice.structural.Constants;
import levin.schnabel.cartservice.structural.entity.CartAction;
import levin.schnabel.cartservice.structural.entity.CartItem;
import levin.schnabel.cartservice.structural.entity.ShoppingCart;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final StreamsBuilderFactoryBean factoryBean;

	public CartService(KafkaTemplate<String, String> kafkaTemplate, StreamsBuilderFactoryBean factoryBean) {
		this.kafkaTemplate = kafkaTemplate;
		this.factoryBean = factoryBean;
	}

	public void addProductToCart(String userId, String productId) {
		CartItem cartItem = new CartItem();
		cartItem.setProductID(productId);
		cartItem.setProductQuantity(1);
		kafkaTemplate.send(Constants.CART_ITEM_CHANGED_TOPIC, userId, cartItem.getProductID());
	}

	public void clearCart(String userId) {
		kafkaTemplate.send(Constants.CART_ITEM_CHANGED_TOPIC, userId, CartAction.CLEAR.toString());
	}

	public List<CartItem> getCart(String userId) throws Exception {
		ShoppingCart cart;
		try {
			final KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
			final ReadOnlyKeyValueStore<String, ShoppingCart> store = kafkaStreams
					.store(StoreQueryParameters.fromNameAndType
							(
									Constants.USER_CART_STORE,
									QueryableStoreTypes.keyValueStore()
							));
			if (store.get(userId) != null) {
				cart = store.get(userId);
				return cart.getProductIdAmountMap().keySet().stream().map(s -> {
					return new CartItem(s, cart.getProductIdAmountMap().get(s));
				}).collect(Collectors.toList());
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Retrieving Cart failed");
		}
	}
}
