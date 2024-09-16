package com.CrudOperations.CrudOperations.cusJpaRepository;

import com.CrudOperations.CrudOperations.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepositoryJPA extends JpaRepository<Customer, Long> {

    Optional<Customer> getUserById(Integer id);

}
