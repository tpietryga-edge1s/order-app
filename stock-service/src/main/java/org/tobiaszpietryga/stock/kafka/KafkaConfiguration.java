package org.tobiaszpietryga.stock.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.tobiaszpietryga.order.common.model.Order;

@Configuration
public class KafkaConfiguration {
	private static final String PRODUCT_STATISTICS_STORE = "productStatistics";

	@Bean
	public KTable<Long, Integer> productStatisticsTable(StreamsBuilder builder) {
		KeyValueBytesStoreSupplier store =
				Stores.persistentKeyValueStore(PRODUCT_STATISTICS_STORE);
		JsonSerde<Order> orderSerde = new JsonSerde<>(Order.class);
		KStream<Long, Order> stream = builder
				.stream("orders", Consumed.with(Serdes.Long(), orderSerde));
		return stream
				.groupBy((key, value) -> value.getProductId())
				.aggregate(() -> 0, (key, value, aggregate) -> aggregate + value.getPrice(), Materialized.<Long, Integer>as(store)
						.withKeySerde(Serdes.Long())
						.withValueSerde(Serdes.Integer()));
	}
}
