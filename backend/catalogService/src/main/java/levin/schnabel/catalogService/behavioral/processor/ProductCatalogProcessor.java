package levin.schnabel.catalogService.behavioral.processor;

import levin.schnabel.catalogService.behavioral.serializer.ProductListSerde;
import levin.schnabel.catalogService.behavioral.serializer.ProductSerde;
import levin.schnabel.catalogService.structural.Constants;
import levin.schnabel.catalogService.structural.entity.Product;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ProductCatalogProcessor {

	@Bean
	public KStream<String, Product> kStream(StreamsBuilder kStreamBuilder) {
		final Serde<Product> productSerde = new ProductSerde();
		final Serde<List<Product>> productListSerde = new ProductListSerde();

		KStream<String, Product> productCreatedStream = kStreamBuilder.stream(Constants.productCreatedTopic, Consumed.with(Serdes.String(), productSerde));
		KStream<String, Product> productUpdatedStream = kStreamBuilder.stream(Constants.productUpdatedTopic, Consumed.with(Serdes.String(), productSerde));

		//KTable<String, Product> productCatalog = productCreatedStream.join
		KTable<String, Product> productUpdatedTable = productUpdatedStream
				.groupByKey(Grouped.with(Serdes.String(), productSerde))
				.reduce((value1, value2) -> value2);

		KTable<String, Product> productCatalogTable = productCreatedStream
				.leftJoin(productUpdatedTable, (readOnlyKey, value1, value2) -> {
					if (value2 != null) {
						value1.setCurrentAssortment(value2.isCurrentAssortment());
						if (value2.getDescription() != null)
							value1.setDescription(value2.getDescription());
						if (value2.getCategory() != null)
							value1.setCategory(value2.getCategory());

					}
					return value1;
				})
				.groupByKey(Grouped.with(Serdes.String(), productSerde))
				.reduce((value1, value2) -> value2)
				.filter(((key, value) -> value.isCurrentAssortment()), Materialized.as(Constants.productCatalogByIdStore));

		productCatalogTable.toStream().to(Constants.productCatalogTopic, Produced.with(Serdes.String(), productSerde));

		productCatalogTable.mapValues((readOnlyKey, value) -> value.getPrice())
				.toStream()
				.to(Constants.PRODUCTID_PRICE_TOPIC, Produced.with(Serdes.String(), Serdes.Double()));

		KTable<String, List<Product>> productByCategory = productCatalogTable
				.groupBy((key, value) -> KeyValue.pair(value.getCategory(), value), Grouped.with(Serdes.String(), productSerde))
				.aggregate(ArrayList::new,
						(aggKey, newProduct, aggValue) -> {
							if (!aggValue.contains(newProduct)) aggValue.add(newProduct);
							return aggValue;
						}, (aggKey, oldProduct, aggValue) -> {
							aggValue.remove(oldProduct);
							return aggValue;
						}, Materialized.<String, List<Product>, KeyValueStore<Bytes, byte[]>>as(Constants.productCatalogByCategoryStore)
								.withKeySerde(Serdes.String())
								.withValueSerde(productListSerde));

		return productCatalogTable.toStream();
	}
}
