package com.CrudOperations.CrudOperations.controller;

import com.CrudOperations.CrudOperations.bean.CustomerBean;
import com.CrudOperations.CrudOperations.entity.Customer;
import com.CrudOperations.CrudOperations.exception.CodeException;
import com.CrudOperations.CrudOperations.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/create")
    public Customer saveCustomer(@RequestBody CustomerBean CustomerBean){
        return customerService.saveCustomer(CustomerBean);
    }

    @GetMapping("/detail")
    public  Customer getCustomerDetailById(@RequestBody CustomerBean customerBean) throws CodeException
    {
        return customerService.getCustomerDetailById(customerBean);
    }

    @PutMapping("/statusUpdate")
    public int updateCustomerStatus(@RequestBody CustomerBean customerBean)throws CodeException{
        return customerService.updateCustomerStatus(customerBean);

    }

    @PutMapping("/updateCustomer")
    public int updateCustomer(@RequestBody CustomerBean customerBean)throws  CodeException{
        return customerService.updateCustomer(customerBean);
    }

    @DeleteMapping("/deleteCustomer")
    public int deleteCustomer(@RequestBody CustomerBean customerBean) throws CodeException
    {
        return customerService.deleteCustomer(customerBean);
    }

    @GetMapping("/allCustomerList")
    public Map getAllCustomerList(@RequestBody CustomerBean customerBean) throws CodeException
    {
        return customerService.getAllCustomerList(customerBean);
    }

      // for Java8
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getUserById(@RequestBody CustomerBean customerBean) {
        Optional<Customer> customer = customerService.getUserById(customerBean.getId());
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }





}
