package com.tabak.test.task2.aligntest.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PRODUCT",
        indexes = {@Index(name = "search_index",  columnList="name, brand")})
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    @NotNull
    private Long id;

    @Column(name ="NAME")
    @NotNull
    private String name;

    @Column(name="BRAND")
    @NotNull
    private String brand;

    @Column(name="PRICE")
    @NotNull
    private Long price;

    @Column(name="QUANTITY")
    @NotNull
    private Long quantity;

}
