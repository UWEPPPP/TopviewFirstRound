package com.liujiahui.www.entity.dto;


/**
 * 用户信息保存dto
 *单例模式
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserInformationSaveDTO {
    private static final UserInformationSaveDTO INSTANCE = new UserInformationSaveDTO();

    private String userName;
    private String gender;
    private String phone;
    private String balance;
    private String address;
    private String privateKey;
    private String accountAddress;
    private String identity;

    private UserInformationSaveDTO() {}

    public static UserInformationSaveDTO getInstance() {
        return INSTANCE;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    @Override
    public String toString() {
        return "UserInformationSaveDTO{" +
                "userName='" + userName + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", balance='" + balance + '\'' +
                ", address='" + address + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", accountAddress='" + accountAddress + '\'' +
                '}';
    }
}

