package com.CrudOperations.CrudOperations.bean;


import lombok.Data;

@Data
public class CustomerBean {

    private Integer id;

    private String name;

    private String email;

    private String phone;

    private String status;

    private String isDeleted;

    private Integer start =0;
    private Integer length =10;
    Long fromDate = Long.valueOf(0);
    Long toDate = Long.valueOf(0);
}
