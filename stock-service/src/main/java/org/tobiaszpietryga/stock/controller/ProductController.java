package org.tobiaszpietryga.stock.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tobiaszpietryga.stock.api.ProductStatisticsDTO;
import org.tobiaszpietryga.stock.domain.Product;
import org.tobiaszpietryga.stock.kafka.InternalStatistics;
import org.tobiaszpietryga.stock.kafka.KafkaConfiguration;
import org.tobiaszpietryga.stock.repository.ProductRepository;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {
	Logger logger = LoggerFactory.getLogger(ProductController.class);
	private final ProductRepository productRepository;
	private final StreamsBuilderFactoryBean kafkaStreamsFactory;

	@GetMapping("statistics")
	public List<ProductStatisticsDTO> statistics() {
		List<ProductStatisticsDTO> statistics = new ArrayList<>();
		ReadOnlyKeyValueStore<Long, InternalStatistics> store = kafkaStreamsFactory
				.getKafkaStreams()
				.store(StoreQueryParameters.fromNameAndType(
						KafkaConfiguration.PRODUCT_STATISTICS_STORE,
						QueryableStoreTypes.keyValueStore()));
		KeyValueIterator<Long, InternalStatistics> it = store.all();
		it.forEachRemaining(keyValue -> statistics.add(ProductStatisticsDTO.builder()
						.id(keyValue.key)
						.totalMoneyAmount(keyValue.value.getAmount())
						.name(productRepository.findById(keyValue.key).map(Product::getName).orElseThrow())
				.build()));
		return statistics;
	}
}
