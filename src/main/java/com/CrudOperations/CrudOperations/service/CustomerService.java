package com.CrudOperations.CrudOperations.service;
import com.CrudOperations.CrudOperations.Utility;
import com.CrudOperations.CrudOperations.bean.CustomerBean;
import com.CrudOperations.CrudOperations.cusJpaRepository.CustomerRepositoryJPA;
import com.CrudOperations.CrudOperations.entity.Customer;
import com.CrudOperations.CrudOperations.exception.ErrorCode;
import com.CrudOperations.CrudOperations.repository.CustomerRepository;
import com.CrudOperations.CrudOperations.exception.CodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerRepositoryJPA customerRepositoryJPA;

    public Customer saveCustomer(CustomerBean customerBean) {
        return customerRepository.saveCustomer(customerBean);
    }

    public Customer getCustomerDetailById(CustomerBean customerBean) throws CodeException{
        Customer customer =  customerRepository.getCustomerDetailById(customerBean);
        if (customer == null){
            throw new CodeException("Customer not found.", ErrorCode.RecordNotFound);
        }
        return customer;
    }

    public Optional<Customer> getUserById(Integer id) {
        return customerRepositoryJPA.getUserById(id);

    }

    public int updateCustomerStatus(CustomerBean customerBean) throws CodeException{
       return customerRepository.updateCustomerStatus(customerBean);
    }

    public int updateCustomer(CustomerBean customerBean) throws CodeException{

       Customer customer =  customerRepository.getCustomerDetailById(customerBean);
       if (customer == null){
           throw new CodeException("User Not Found.Please Contact your admin.",ErrorCode.RecordNotFound);
       }
       if (Utility.isEmpty(customerBean.getName())){
           throw new CodeException("Name is Required.",ErrorCode.RequiredVariableIsNull);
       }
       if (Utility.isEmpty(customerBean.getPhone())){
           throw new CodeException("Phone Number is required.",ErrorCode.RequiredVariableIsNull);
       }
       if (Utility.isEmpty(customerBean.getEmail())){
           throw new CodeException("Email is Required.",ErrorCode.RequiredVariableIsNull);
       }
        return customerRepository.updateCustomer(customerBean);
    }

    public int deleteCustomer(CustomerBean customerBean) throws CodeException{
        Customer customer =  customerRepository.getCustomerDetailById(customerBean);
        if (customer == null){
            throw new CodeException("User Not Found.Please Contact your admin.",ErrorCode.RecordNotFound);
        }
        return customerRepository.deleteCustomer(customerBean);
    }

    public Map getAllCustomerList(CustomerBean customerBean) throws CodeException{
        return customerRepository.getAllCustomerList(customerBean);
    }
}
