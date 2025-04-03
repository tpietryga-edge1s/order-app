package org.tobiaszpietryga.order;

import org.springframework.boot.SpringApplication;

public class OrderAppTest {
    public static void main(String[] args) {
        SpringApplication.from(OrderServiceApplication::main)
                .with(KafkaContainerDevMode.class)
                .run(args);
    }
}
