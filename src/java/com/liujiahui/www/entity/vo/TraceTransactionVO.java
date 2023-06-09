package com.liujiahui.www.entity.vo;

import java.math.BigInteger;

/**
 * 用户事务处理签证官
 *
 * @author 刘家辉
 * @date 2023/03/21
 */
public class TraceTransactionVO {
    private String balance;
    private BigInteger price;
    private String name;
    private String seller;
    private String buyer;
    private String hash;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserTransactionVO{" +
                "balance='" + balance + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", seller='" + seller + '\'' +
                ", buyer='" + buyer + '\'' +
                ", hash='" + hash + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
