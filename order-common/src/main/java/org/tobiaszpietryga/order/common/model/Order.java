package org.tobiaszpietryga.order.common.model;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;


@Builder
@Data
@NoArgsConstructor
public class Order {
	@ConstructorProperties({"id", "customerId", "productId", "name", "status", "price", "productCount", "paymentStarted", "stockStarted"})
	public Order(Long id, Long customerId, Long productId, String name, Status status, Integer price, Integer productCount, boolean paymentStarted, boolean stockStarted) {
		this.id = id;
		this.customerId = customerId;
		this.productId = productId;
		this.name = name;
		this.status = status;
		this.price = price;
		this.productCount = productCount;
		this.paymentStarted = paymentStarted;
		this.stockStarted = stockStarted;
	}

	Long id;
	Long customerId;
	Long productId;
	String name;
	Status status;
	Integer price;
	Integer productCount;
	boolean paymentStarted;
	boolean stockStarted;
}
