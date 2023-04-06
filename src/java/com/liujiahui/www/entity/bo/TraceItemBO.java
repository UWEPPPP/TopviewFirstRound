package com.liujiahui.www.entity.bo;

import java.math.BigInteger;

/**
 * 用于商品信息的BO
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class TraceItemBO {
    private String name;
    private BigInteger price;
    private String description;
    private String realDescription;
    private String realName;
    private Integer type;
    private String location;
    private String storage;
    private BigInteger token;

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

    public String getRealDescription() {
        return realDescription;
    }

    public void setRealDescription(String realDescription) {
        this.realDescription = realDescription;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public BigInteger getToken() {
        return token;
    }

    public void setToken(BigInteger token) {
        this.token = token;
    }
}
