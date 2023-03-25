package com.liujiahui.www.entity.bo;

import java.math.BigInteger;

/**
 * 项波
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class TraceAddItemBO {
    private String name;
    private BigInteger price;
    private String description;
    private String realDescription;
    private String realName;

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

    @Override
    public String toString() {
        return "ItemBO{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void setRealDescription(String realDescription) {
        this.realDescription = realDescription;
    }

    public String getRealDescription() {
        return realDescription;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }
}
