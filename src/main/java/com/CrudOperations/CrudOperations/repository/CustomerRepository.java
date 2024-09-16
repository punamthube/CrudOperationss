package com.CrudOperations.CrudOperations.repository;

import com.CrudOperations.CrudOperations.bean.CustomerBean;
import com.CrudOperations.CrudOperations.entity.Customer;
import com.CrudOperations.CrudOperations.exception.CodeException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository {

    @Autowired
    private SessionFactory factory;


    protected Session getNewSession() {
        return factory.openSession();
    }

    protected Session getSession() {
        Session session = factory.getCurrentSession();
        if (session == null) {
            session = factory.openSession();
        }
        return session;
    }


    @Transactional
    public Customer saveCustomer(CustomerBean customerBean) {

        Customer customer = new Customer();
        customer.setName(customerBean.getName());
        customer.setEmail(customerBean.getEmail());
        customer.setPhone(customerBean.getPhone());
        customer.setStatus(customerBean.getStatus());
        getSession().save(customer);

        return customer;
    }

    @Transactional(readOnly = true)
    public Customer getCustomerDetailById(CustomerBean customerBean) {
        String hql = "SELECT c FROM Customer c WHERE c.id=:id ";
        Query query = getSession().createQuery(hql);
        query.setParameter("id", customerBean.getId());
        return (Customer) query.uniqueResult();
    }

    @Transactional
    public int updateCustomerStatus(CustomerBean customerBean) throws CodeException {


        String hql = "UPDATE Customer b SET b.status =:status WHERE b.id =:id ";
        Query query = getSession().createQuery(hql);
        query.setParameter("status", customerBean.getStatus());
        query.setParameter("id", customerBean.getId());
        return query.executeUpdate();

    }

    @Transactional()
    public int updateCustomer(CustomerBean customerBean) throws CodeException{
        String hql = " UPDATE Customer c SET c.name =:name, c.email = :email, c.phone =:phone  WHERE c.id =:id";
        Query query = getSession().createQuery(hql);
        query.setParameter("name", customerBean.getName());
        query.setParameter("email", customerBean.getEmail());
        query.setParameter("phone",customerBean.getPhone());
        query.setParameter("id",customerBean.getId());
        return query.executeUpdate();

    }

    @Transactional(readOnly = true)
    public int deleteCustomer(CustomerBean customerBean) {
        String hql = "UPDATE Customer c SET c.isDeleted='Y' WHERE c.id=:id ";
        Query query = getSession().createQuery(hql);
        query.setParameter("id", customerBean.getId());
        return query.executeUpdate();
    }

    @Transactional(readOnly = true)
    public Map getAllCustomerList(CustomerBean customerBean) {

        Integer totalRows = 0;
        Integer filterCount = 0;
        Map responseObj = new HashMap<>();
        String hql = "SELECT c FROM Customer c ";
        String conditions = " WHERE c.isDeleted = 'N'  ";
        if(customerBean.getFromDate() > 0) conditions += " AND ( c.createdAt >= :fromDate) ";
        if(customerBean.getToDate() > 0) conditions += " AND ( c.createdAt <= :toDate) ";
        conditions += " ORDER BY c.id DESC ";
        hql = hql + conditions;
        Query query = getSession().createQuery(hql);
        query = getQueryFilters(query, customerBean);
        query.setFirstResult(customerBean.getStart());
        query.setMaxResults(customerBean.getLength());
        List<Customer> customerList = query.getResultList();
        responseObj.put("data", customerList);
        if (customerBean.getStart() == 0) {
            String filterCountQuery = " SELECT COUNT(c.id) FROM Customer c ";
            filterCountQuery = filterCountQuery + conditions;
            Query fcq = getSession().createQuery(filterCountQuery);
            fcq = getQueryFilters(fcq, customerBean);
            filterCount = ((Long) fcq.getSingleResult()).intValue();
            responseObj.put("filter", filterCount);
            totalRows = getTotalRows("Customer", true);
            responseObj.put("total", totalRows);
        }
        return responseObj;
    }

    public Query getQueryFilters(Query query, CustomerBean customerBean) {

        if (customerBean.getFromDate() > 0) query.setParameter("fromDate", customerBean.getFromDate());
        if (customerBean.getToDate() > 0) query.setParameter("toDate", customerBean.getToDate());

        return query;
    }

    public int getTotalRows(String beanName, boolean isDeleted) {
        String hql = "select count(id) from "+ beanName +" ";
        if (isDeleted) hql += " where isDeleted != 'Y' ";
        Query query = getSession().createQuery(hql);
        return ((Long) query.getSingleResult()).intValue();
    }
}
