package org.tobiaszpietryga.stock.exception;

public class TechnicalException extends RuntimeException {
	public TechnicalException(String msg, Exception source) {
		super(msg, source);
	}
}
