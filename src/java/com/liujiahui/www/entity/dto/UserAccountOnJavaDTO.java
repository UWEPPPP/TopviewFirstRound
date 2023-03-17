package com.liujiahui.www.entity.dto;

/**
 * 在java用户帐户
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class UserAccountOnJavaDTO {
     private String account;
     private String password;
     private String identity;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserAccountOnJava{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", identity='" + identity + '\'' +
                '}';
    }
}
