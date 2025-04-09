package org.tobiaszpietryga.stock.kafka;

import java.beans.ConstructorProperties;

import lombok.Value;

public class InternalStatistics {
	Integer amount;
	Integer count;

	@ConstructorProperties({"amount", "count"})
	public InternalStatistics(Integer amount, Integer count) {
		this.amount = amount;
		this.count = count;
	}

	public static InternalStatistics createNew() {
		return new InternalStatistics(0, 0);
	}

	public InternalStatistics incrementBy(Integer amount, Integer count) {
		return new InternalStatistics(this.amount + amount, this.count + count);
	}
}
