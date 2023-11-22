package com.bee.product.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    public String firstName;

    @Column(name="last_name")
    public String lastName;

    @Transient
    public Long value;

}
