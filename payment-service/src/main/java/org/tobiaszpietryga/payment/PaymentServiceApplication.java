package org.tobiaszpietryga.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.tobiaszpietryga.payment.kafka.PaymentListener;

@SpringBootApplication
@EnableKafka
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

//	@Bean
//	public PaymentListener paymentListener() {
//		return new PaymentListener();
//	}
}
