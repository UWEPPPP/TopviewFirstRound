package com.liujiahui.www.entity.vo;

import java.math.BigInteger;

public class TranscationVO {
    private String balance;
    private String seller;
    private String buyer;
    private BigInteger price;
    private String name;

    public String getSeller() {
        return seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }

    public void setSeller(String seller) {
    }

    public void setBuyer(String buyer) {

    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
