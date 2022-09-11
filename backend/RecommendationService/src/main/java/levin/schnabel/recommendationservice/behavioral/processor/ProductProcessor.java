package levin.schnabel.recommendationservice.behavioral.processor;

import levin.schnabel.recommendationservice.behavioral.serializer.product.ProductSerde;
import levin.schnabel.recommendationservice.structural.Constants;
import levin.schnabel.recommendationservice.structural.entity.Product;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.ArrayList;
import java.util.List;

public class ProductProcessor {

	protected static void process(KStream<String, Product> productStream) {
		Serde<Product> productSerde = new ProductSerde();
		Serde<List<Product>> productListSerde = new Serdes.ListSerde<Product>(ArrayList.class, productSerde);

		productStream.mapValues((readOnlyKey, value) -> value.getCategory())
				.groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
				.reduce((value1, value2) -> value2, Materialized.<String, String, KeyValueStore<Bytes, byte[]>>as(Constants.CATEGORY_BY_PRODUCTID_STORE)
						.withKeySerde(Serdes.String())
						.withValueSerde(Serdes.String()));

		productStream.toTable()
				.groupBy((key, value) -> KeyValue.pair(value.getCategory(), value), Grouped.with(Serdes.String(), productSerde))
				.aggregate(ArrayList::new,
						(aggKey, newProduct, aggValue) -> {
							if (!aggValue.contains(newProduct)) aggValue.add(newProduct);
							return aggValue;
						}, (aggKey, oldProduct, aggValue) -> {
							aggValue.remove(oldProduct);
							return aggValue;
						}, Materialized.<String, List<Product>, KeyValueStore<Bytes, byte[]>>as(Constants.PRODUCT_CATALOG_BY_CATEGORY_STORE)
								.withKeySerde(Serdes.String())
								.withValueSerde(productListSerde));
	}

}
