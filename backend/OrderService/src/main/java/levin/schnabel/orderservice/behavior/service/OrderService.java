package levin.schnabel.orderservice.behavior.service;

import levin.schnabel.orderservice.structural.Constants;
import levin.schnabel.orderservice.structural.entity.Order;
import levin.schnabel.orderservice.structural.entity.OrderDTO;
import levin.schnabel.orderservice.structural.entity.OrderPosition;
import levin.schnabel.orderservice.structural.entity.OrderState;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

	private final KProducerService kProducerService;
	private final StreamsBuilderFactoryBean factoryBean;

	public OrderService(KProducerService kProducerService, StreamsBuilderFactoryBean factoryBean) {
		this.kProducerService = kProducerService;
		this.factoryBean = factoryBean;
	}

	public void createOrder(OrderDTO order) {
		createOrder(Order.fromOrderDTO(order));
	}

	public void createOrder(Order order) {
		order.setCreationDate(Date.valueOf(LocalDate.now()));
		order.setOrderID(UUID.randomUUID().toString());
		order.setOrderState(OrderState.ORDER_RECEIVED);
		order.setOrderPositionList(
				order.getOrderPositionList()
						.stream()
						.peek(position ->
								position.setPrice(
										getProductPrice(position.getArticleNumber()) * position.getQuantity()
								)).collect(Collectors.toList())
		);
		order.addPosition(new OrderPosition(order.getShippingMethod().getName(), order.getShippingMethod().getPrice(), 1));

		kProducerService.produceOrderCreation(order);
	}

	public List<OrderDTO> getOrders(String userId) {
		List<Order> orderList = new ArrayList<>();
		try {
			final KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
			final ReadOnlyKeyValueStore<String, List<Order>> store = kafkaStreams
					.store(StoreQueryParameters.fromNameAndType(
							Constants.ORDER_BY_USER_STORE,
							QueryableStoreTypes.keyValueStore()
					));
			if (store.get(userId) != null) {
				orderList = store.get(userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderList.stream().map(OrderDTO::fromOrder).collect(Collectors.toList());
	}

	public double getProductPrice(String articleNumber) {
		final KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
		final ReadOnlyKeyValueStore<String, Double> store = kafkaStreams
				.store(StoreQueryParameters.fromNameAndType
						(
								Constants.PRODUCT_ID_PRICE_STORE,
								QueryableStoreTypes.keyValueStore()
						)
				);
		return store.get(articleNumber);
	}


	public double getOrderTotalPrice(Order order) {
		return order.getOrderPositionList().stream().reduce(0d, (sum, value) -> sum + value.getPrice(), Double::sum);
	}

}
