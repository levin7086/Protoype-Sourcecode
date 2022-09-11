package levin.schnabel.catalogService.behavioral.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import levin.schnabel.catalogService.structural.Constants;
import levin.schnabel.catalogService.structural.entity.Product;
import levin.schnabel.catalogService.structural.exception.ProductServiceExpception;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

	private final KProducerService kProducerService;
	private final StreamsBuilderFactoryBean factoryBean;
	private final ResourceLoader resourceLoader;

	public ProductService(KProducerService kProducerService, StreamsBuilderFactoryBean factoryBean, ResourceLoader resourceLoader) {
		this.kProducerService = kProducerService;
		this.factoryBean = factoryBean;
		this.resourceLoader = resourceLoader;
	}

	public void createProduct(Long id, String name, String description, boolean currentSortiment, String category) {
		Product product = new Product();
		product.setName(name);
		product.setArticleNumber(getArticleId(id));
		product.setDescription(description);
		product.setCurrentAssortment(currentSortiment);
		product.setCategory(category);
		kProducerService.produceProductCreation(product);
	}

	public void createProducts(Set<Product> productSet) {
		productSet.forEach((product -> {
			if (product != null && product.getArticleNumber() != null && product.getName() != null && !product.getName().isBlank()) {
				kProducerService.produceProductCreation(product);
			}
		}));
	}

	public void updateProduct(Long id, String name, String description, boolean currentSortiment, String category) {
		Product product = new Product();
		product.setName(name);
		;
		product.setArticleNumber(getArticleId(id));
		product.setDescription(description);
		product.setCurrentAssortment(currentSortiment);
		product.setCategory(category);
		kProducerService.produceProdcutUpdate(product);
	}

	public List<Product> getProductsById(List<String> productIds) {
		return productIds.stream().map(id -> {
			try {
				return getProductById(id);
			} catch (ProductServiceExpception e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}

	public Product getProductById(String id) throws ProductServiceExpception {
		try {
			final KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
			final ReadOnlyKeyValueStore<String, Product> store = kafkaStreams
					.store(StoreQueryParameters.fromNameAndType
							(
									Constants.productCatalogByIdStore,
									QueryableStoreTypes.keyValueStore()
							));
			if (store.get(id) != null)
				return store.get(id);
			else
				throw new ProductServiceExpception("Could not identify product by id");
		} catch (NullPointerException e) {
			throw new ProductServiceExpception("Could not identify product by id");
		} catch (Exception e) {
			throw new ProductServiceExpception("Retrieving Product failed");
		}
	}

	public List<Product> getProductsByCategory(String categoryName) throws ProductServiceExpception {
		try {
			final KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
			final ReadOnlyKeyValueStore<String, List<Product>> store = kafkaStreams
					.store(StoreQueryParameters.fromNameAndType
							(
									Constants.productCatalogByCategoryStore,
									QueryableStoreTypes.keyValueStore()
							));
			if (store.get(categoryName) != null)
				return store.get(categoryName);
			else
				throw new ProductServiceExpception("Could not identify product by id");
		} catch (NullPointerException e) {
			throw new ProductServiceExpception("Could not identify product by id");
		} catch (Exception e) {
			throw new ProductServiceExpception("Retrieving Product failed");
		}
	}

	public String getArticleId(Long id) {
		return "P#" + id;
	}

	public void generateDummyCatalog() throws IOException {
		Resource resource = resourceLoader.getResource("classpath:dummyCatalog.csv");
		InputStream inputStream = resource.getInputStream();
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema schema = csvMapper.schemaFor(Product.class).withHeader().withColumnSeparator(';');
		MappingIterator<Product> parsedData = csvMapper
				.readerWithTypedSchemaFor(Product.class)
				.with(schema)
				.readValues(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
		this.createProducts(new HashSet<>(parsedData.readAll()));
	}

}
