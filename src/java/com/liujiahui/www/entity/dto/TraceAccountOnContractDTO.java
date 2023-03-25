package com.liujiahui.www.entity.dto;

/**
 * 用户账户
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class TraceAccountOnContractDTO {
    private String accountAddress;
    private String privateKey;

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
        return "UserAccount{" +
                "accountAddress='" + accountAddress + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}
