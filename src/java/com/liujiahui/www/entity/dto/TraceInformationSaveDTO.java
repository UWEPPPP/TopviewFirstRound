package com.liujiahui.www.entity.dto;


import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderInterface;

/**
 * 用户信息保存dto
 *单例模式
 * @author 刘家辉
 * @date 2023/03/18
 */
public class TraceInformationSaveDTO {
    private static final TraceInformationSaveDTO INSTANCE = new TraceInformationSaveDTO();

    private String userName;
    private String gender;
    private String phone;
    private String balance;
    private String userAddress;
    private String identity;
    private ContractTradeService contractTradeServiceSolidity;
    private String contractAccount;
    private TransactionDecoderInterface decoder;

    private TraceInformationSaveDTO() {}

    public static TraceInformationSaveDTO getInstance() {
        return INSTANCE;
    }

    public ContractTradeService getItemTradeSolidity() {
        return contractTradeServiceSolidity;
    }
    public void setItemTradeSolidity(ContractTradeService contractTradeServiceSolidity) {
        this.contractTradeServiceSolidity = contractTradeServiceSolidity;
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

