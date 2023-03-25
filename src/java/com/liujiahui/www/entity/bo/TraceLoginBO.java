package com.liujiahui.www.entity.bo;

/**
 * 消费者登录值对象
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceLoginBO {
      private String  account;
      private String  password;
      private String  identity;
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
        return "UserLoginBO{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", identity='" + identity + '\'' +
                '}';
    }

    public TraceLoginBO(String account, String password, String identity) {
        this.account = account;
        this.password = password;
        this.identity = identity;
    }
}
