package org.tobiaszpietryga.payment.repository;

import org.springframework.data.repository.CrudRepository;
import org.tobiaszpietryga.payment.doman.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
