package com.liujiahui.www.entity.po;

/**
 * 用户登录后
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class UserAfterLoginBO {
      private  String name;
      private  String balance;
      private  String accountAddress;
      private  String privateKey;

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

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "UserAfterLogin{" +
                "name='" + name + '\'' +
                ", balance='" + balance + '\'' +
                ", accountAddress='" + accountAddress + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}
