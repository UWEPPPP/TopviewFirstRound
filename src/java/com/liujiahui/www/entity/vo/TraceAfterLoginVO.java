package com.liujiahui.www.entity.vo;

/**
 * 用户登录后签证官
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class TraceAfterLoginVO {
    private String name;
    private String balance;
    private String identity;
    private Integer informationSize;
    private Integer appealReusltSize;

    public TraceAfterLoginVO(String name, String balance, String identity) {
        this.name = name;
        this.balance = balance;
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserAfterLoginVO{" +
                "name='" + name + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Integer getInformationSize() {
        return informationSize;
    }

    public void setInformationSize(Integer informationSize) {
        this.informationSize = informationSize;
    }

    public Integer getAppealReusltSize() {
        return appealReusltSize;
    }

    public void setAppealReusltSize(Integer appealReusltSize) {
        this.appealReusltSize = appealReusltSize;
    }
}
