package com.liujiahui.www.entity.po;

import java.math.BigInteger;

/**
 * 项
 *
 * @author 刘家辉
 * @date 2023/03/19
 */
public class Item {
    private Integer id;
    private String name;
    private BigInteger price;

    private String description;

    public Item(Integer id,String name,BigInteger price,String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
