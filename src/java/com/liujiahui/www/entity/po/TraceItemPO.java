package com.liujiahui.www.entity.po;

import org.fisco.bcos.sdk.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 项
 *
 * @author 刘家辉
 * @date 2023/03/19
 */
public class TraceItemPO {
    private Integer id;
    private String name;
    private BigInteger price;

    private String description;
    private String owner;
    private BigDecimal index;
    private Boolean isSold;
    private byte[] hashes;
    private String ownerName;
    private String type;
    private String seller;
    private int token;

    public TraceItemPO(Integer id, String name, BigInteger price, String description, String owner, BigDecimal index, boolean isSold, String ownerName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.owner = owner;
        this.index = index;
        this.isSold = isSold;
        this.ownerName = ownerName;
    }

    public TraceItemPO(Integer id, String name, BigInteger price, String description, String owner, BigDecimal index, Boolean isSold) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.owner = owner;
        this.index = index;
        this.isSold = isSold;
    }

    public TraceItemPO(String name, BigInteger price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public TraceItemPO(Integer id, String name, BigInteger price, String description, String owner, BigDecimal index, boolean isSold, byte[] hashes) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.owner = owner;
        this.index = index;
        this.isSold = isSold;
        this.hashes = hashes;
    }

    public TraceItemPO() {
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getHashes() {
        return Numeric.toHexString(hashes);
    }

    public void setHashes(byte[] hashes) {
        this.hashes = hashes;
    }

    public Boolean getSold() {
        return isSold;
    }

    public void setSold(Boolean sold) {
        isSold = sold;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getIndex() {
        return index;
    }

    public void setIndex(BigDecimal index) {
        this.index = index;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
