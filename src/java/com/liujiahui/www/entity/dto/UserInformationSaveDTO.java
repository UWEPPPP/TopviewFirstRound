package com.liujiahui.www.entity.dto;


import com.liujiahui.www.solidity.ItemTrade;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderInterface;

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
    private String userAddress;
    private String identity;
    private ItemTrade itemTradeSolidity;
    private String contractAccount;
    private TransactionDecoderInterface decoder;

    private UserInformationSaveDTO() {}

    public static UserInformationSaveDTO getInstance() {
        return INSTANCE;
    }

    public ItemTrade getItemTradeSolidity() {
        return itemTradeSolidity;
    }
    public void setItemTradeSolidity(ItemTrade itemTradeSolidity) {
        this.itemTradeSolidity = itemTradeSolidity;
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

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }


    @Override
    public String toString() {
        return "UserInformationSaveDTO{" +
                "userName='" + userName + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", balance='" + balance + '\'' +
                ", address='" + userAddress + '\'' +
                ", identity='" + identity + '\'' ;
    }

    public void setContractAccount(String contractAccount) {
        this.contractAccount = contractAccount;
    }

    public String getContractAccount() {
        return contractAccount;
    }

    public void setDecoder(TransactionDecoderInterface decoder) {
        this.decoder = decoder;
    }

    public TransactionDecoderInterface getDecoder() {
        return decoder;
    }
}

