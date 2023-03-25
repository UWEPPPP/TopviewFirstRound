package com.liujiahui.www.entity.vo;

/**
 * 用户详细签证官
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class TraceDetailedVO {
    private String userName;
    private String gender;
    private String phone;
    private String balance;
    private String identify;

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserDetailedVO{" +
                "userName='" + userName + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
