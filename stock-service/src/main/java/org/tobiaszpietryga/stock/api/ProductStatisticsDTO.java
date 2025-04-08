package org.tobiaszpietryga.stock.api;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductStatisticsDTO {
	Long id;
	String name;
	Integer totalMoneyAmount;
}
