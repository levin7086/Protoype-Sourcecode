package levin.schnabel.cartservice.behavioral.processor;

import levin.schnabel.cartservice.behavioral.serializer.CartItemSerde;
import levin.schnabel.cartservice.behavioral.serializer.ShoppingCartSerde;
import levin.schnabel.cartservice.behavioral.serializer.order.OrderSerde;
import levin.schnabel.cartservice.structural.Constants;
import levin.schnabel.cartservice.structural.entity.CartAction;
import levin.schnabel.cartservice.structural.entity.CartItem;
import levin.schnabel.cartservice.structural.entity.ShoppingCart;
import levin.schnabel.cartservice.structural.entity.order.Order;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CartProcessor {

	@Bean
	public KTable<String, ShoppingCart> processCartItems(StreamsBuilder streamsBuilder) {
		final Serde<CartItem> cartItemSerde = new CartItemSerde();
		final Serde<ShoppingCart> cartSerde = new ShoppingCartSerde();
		final Serde<Order> orderSerde = new OrderSerde();

		KStream<String, String> cartItemStream = streamsBuilder.stream(Constants.CART_ITEM_CHANGED_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));
		KStream<String, Order> orderCreatedStream = streamsBuilder
				.stream(Constants.ORDER_CREATED_TOPIC, Consumed.with(Serdes.String(), orderSerde))
				.map((key, value) -> new KeyValue<>(value.getUserId(), value));

		KTable<String, ShoppingCart> shoppingCartTable = cartItemStream.groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
				.aggregate(ShoppingCart::new, (key, value, aggregate) -> {
					if (value.equals(CartAction.CLEAR.toString())) {
						aggregate.clear();
						return aggregate;
					}
					Map<String, Integer> map = aggregate.getProductIdAmountMap();
					if (map.containsKey(value)) {
						map.put(value, map.get(value) + 1);
						aggregate.setProductIdAmountMap(map);
						return aggregate;
					}
					map.put(value, 1);
					aggregate.setProductIdAmountMap(map);
					return aggregate;
				}, Materialized.<String, ShoppingCart, KeyValueStore<Bytes, byte[]>>as(Constants.USER_CART_STORE)
						.withKeySerde(Serdes.String())
						.withValueSerde(cartSerde));

		shoppingCartTable.toStream().to(Constants.USER_CART_TOPIC, Produced.with(Serdes.String(), cartSerde));

		return shoppingCartTable;
	}

}
