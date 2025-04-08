package org.tobiaszpietryga.order.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.tobiaszpietryga.order.common.model.Order;

@Configuration
@EnableKafka
public class KafkaConfiguration {
	public static final String PRODUCT_STATISTICS_STORE = "productStatistics";
	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	@Bean
	public NewTopic orders() {
		return new NewTopic("orders", 1, (short) 1);
	}

	@Bean
	public NewTopic paymentTopic() {
		return new NewTopic("payment-orders", 1, (short) 1);
	}

	@Bean
	public NewTopic stockTopic() {
		return new NewTopic("stock-orders", 1, (short) 1);
	}

}
