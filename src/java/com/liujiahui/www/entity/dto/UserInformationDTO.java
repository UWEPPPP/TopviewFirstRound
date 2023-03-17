package com.liujiahui.www.entity.dto;

/**
 * 用户信息dto
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class UserInformationDTO {
      private   String userName;
      private   String balance;
      private   String identity;

    public UserInformationDTO(String userName, String balance, String identity) {
        this.userName = userName;
        this.balance = balance;
        this.identity = identity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "UserInformationDTO{" +
                "userName='" + userName + '\'' +
                ", balance=" + balance +
                ", identity='" + identity + '\'' +
                '}';
    }
}
