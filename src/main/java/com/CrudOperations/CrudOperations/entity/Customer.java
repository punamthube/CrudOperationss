package com.CrudOperations.CrudOperations.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "/TblCustomer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private String status;

    @Column(name = "is_deleted")
    private String isDeleted;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
    private Long updatedAt;

}
