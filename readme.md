# Demo project for SpringBoot Microservices and Kafka implementing Saga Pattern

## Description
There are three microservices: \
`order-service` - receives an order via REST controller, post it to Kafka, and then orchestrates the Saga to either commit or rollback local transactions \
`payment-service` - starts and commits/rollbacks local transaction for Customer bank accounts \
`stock-service` - starts and commits/rollbacks local transaction for Product stock

## Local execution
Setup kafka, adjust url/port in application.properties, then run microservices in IntelliJ Idea or using 
```shell
mvn clean spring-boot:run
```
