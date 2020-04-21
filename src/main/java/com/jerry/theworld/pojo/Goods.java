package com.jerry.theworld.pojo;

/**
 * Author:Jerry
 * 2020/4/17
 */
public class Goods {
    private String name;
    private Double price;

    public String getName() {
        return name;
    }

    public Goods setName(String name) {
        this.name = name;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Goods setPrice(Double price) {
        this.price = price;
        return this;
    }
}
