package org.tobiaszpietryga.stock.sevice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;
import org.tobiaszpietryga.order.common.model.Order;
import org.tobiaszpietryga.order.common.model.Status;
import org.tobiaszpietryga.stock.doman.Product;
import org.tobiaszpietryga.stock.repository.ProductRepository;

@SpringBootTest(properties = { "spring.kafka.consumer.auto-offset-reset=earliest" })
@Testcontainers
@Transactional
public class StockTestContainersTest {
	@Autowired
	KafkaTemplate<Long, Order> kafkaTemplate;
	@Autowired
	ProductRepository productRepository;

	@Container
	static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka:4.0.0"));
	@DynamicPropertySource
	static void overrideProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}

	@Test
	void shouldPartiallyConfirmOrder() throws InterruptedException {
		//given
		//when
		kafkaTemplate.send("orders", 1L, prepareOrder(Status.NEW, true, 5, 1L));

		//then
		Thread.sleep(1000);
		Product product = productRepository.findById(1L).orElseThrow();
		Assertions.assertThat(product.getItemsAvailable()).isEqualTo(95);
		Assertions.assertThat(product.getItemsReserved()).isEqualTo(5);
	}

	private static Order prepareOrder(Status status, boolean stockStarted, int productCount, long productId) {
		return Order.builder()
				.id(productId)
				.status(status)
				.productId(productId)
				.stockStarted(stockStarted)
				.productCount(productCount)
				.build();
	}
}
