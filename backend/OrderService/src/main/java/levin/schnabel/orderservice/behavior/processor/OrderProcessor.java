package levin.schnabel.orderservice.behavior.processor;

import levin.schnabel.orderservice.behavior.serializer.OrderSerde;
import levin.schnabel.orderservice.behavior.service.OrderService;
import levin.schnabel.orderservice.structural.Constants;
import levin.schnabel.orderservice.structural.entity.Order;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OrderProcessor {

	private final OrderService orderService;

	public OrderProcessor(OrderService orderService) {
		this.orderService = orderService;
	}

	@Bean
	public KTable<String, List<Order>> initializeOrderProcessor(StreamsBuilder streamsBuilder) {
		Serde<Order> orderSerde = new OrderSerde();
		Serde<List<Order>> orderListSerde = new Serdes.ListSerde<>(ArrayList.class, orderSerde);

		KTable<String, Double> productPriceTable = streamsBuilder.stream(Constants.PRODUCT_ID_PRICE_TOPIC, Consumed.with(Serdes.String(), Serdes.Double()))
				.groupByKey(Grouped.with(Serdes.String(), Serdes.Double()))
				.reduce((value1, value2) -> value2,
						Materialized.<String, Double, KeyValueStore<Bytes, byte[]>>as(Constants.PRODUCT_ID_PRICE_STORE)
								.withKeySerde(Serdes.String())
								.withValueSerde(Serdes.Double()));

		KStream<String, Order> orderCreatedStream = streamsBuilder.stream(Constants.ORDER_CREATED_TOPIC);

		KTable<String, List<Order>> ordersByUser = orderCreatedStream
				.groupBy((key, value) -> value.getUserId(), Grouped.with(Serdes.String(), orderSerde))
				.aggregate(ArrayList::new, (key, value, aggregate) -> {
//					aggregate = aggregate.stream().filter(order -> !order.getOrderID().equals(value.getOrderID())).collect(Collectors.toList());
					aggregate.add(value);
					return aggregate;
				}, Materialized.<String, List<Order>, KeyValueStore<Bytes, byte[]>>as(Constants.ORDER_BY_USER_STORE)
						.withKeySerde(Serdes.String())
						.withValueSerde(orderListSerde));


		return ordersByUser;
	}

}
