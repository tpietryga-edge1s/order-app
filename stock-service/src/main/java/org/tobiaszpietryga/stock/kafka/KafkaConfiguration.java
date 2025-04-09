package org.tobiaszpietryga.stock.kafka;

import lombok.extern.slf4j.Slf4j;
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
import org.tobiaszpietryga.order.common.model.Status;

@Configuration
@Slf4j
public class KafkaConfiguration {
	public static final String PRODUCT_STATISTICS_STORE = "productRichStatistics";

	@Bean
	public KTable<Long, InternalStatistics> productStatisticsTable(StreamsBuilder builder) {
		KeyValueBytesStoreSupplier store =
				Stores.persistentKeyValueStore(PRODUCT_STATISTICS_STORE);
		JsonSerde<Order> orderSerde = new JsonSerde<>(Order.class);
		JsonSerde<InternalStatistics> statisticsSerde = new JsonSerde<>(InternalStatistics.class);
		KStream<Long, Order> stream = builder
				.stream("orders", Consumed.with(Serdes.Long(), orderSerde));
		return stream
				.filter((key, value) -> value.getStatus().equals(Status.CONFIRMED))
				.peek((key, value) -> log.info("Streams processing: {}", value))
				.groupBy((key, value) -> value.getProductId())
				.aggregate(InternalStatistics::createNew, (key, value, aggregate) -> aggregate.incrementBy(value.getPrice() * value.getProductCount(), value.getProductCount()), Materialized.<Long, InternalStatistics>as(store)
						.withKeySerde(Serdes.Long())
						.withValueSerde(statisticsSerde));
	}

}
