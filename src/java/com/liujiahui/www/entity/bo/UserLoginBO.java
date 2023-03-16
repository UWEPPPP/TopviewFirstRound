package com.liujiahui.www.entity.bo;

/**
 * 消费者登录值对象
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class UserLoginBO {
      private int  account;
      private String  password;

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
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
        return "UserLoginBO{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
