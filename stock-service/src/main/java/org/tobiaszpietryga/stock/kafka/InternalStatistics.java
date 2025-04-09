package org.tobiaszpietryga.stock.kafka;

import lombok.Value;

@Value
public class InternalStatistics {
	Integer amount;
	Integer count;

	public static InternalStatistics createNew() {
		return new InternalStatistics(0, 0);
	}

	public InternalStatistics incrementBy(Integer amount, Integer count) {
		return new InternalStatistics(this.amount + amount, this.count + count);
	}
}
