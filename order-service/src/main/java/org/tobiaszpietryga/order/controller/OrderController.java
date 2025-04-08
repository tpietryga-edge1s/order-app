package org.tobiaszpietryga.order.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tobiaszpietryga.order.configuration.KafkaConfiguration;
import org.tobiaszpietryga.order.sevice.OrderService;
import org.tobiaszpietryga.order.common.model.Order;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {
	Logger logger = LoggerFactory.getLogger(OrderController.class);
	private final OrderService orderService;

	@PostMapping
	public void makeOrder(@RequestBody Order order) {
		logger.info("Received an orderName {}", order);
		orderService.sendOrder(order);
	}

}
