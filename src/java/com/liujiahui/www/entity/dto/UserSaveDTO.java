package com.liujiahui.www.entity.dto;


import com.liujiahui.www.service.wrapper.ContractProxyService;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderInterface;

/**
 * 用户信息保存dto
 * 单例模式
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserSaveDTO {
    private static final UserSaveDTO INSTANCE = new UserSaveDTO();
    private String userName;
    private String gender;
    private String phone;
    private String balance;
    private String userAddress;
    private String identity;
    private ContractProxyService contractTradeServiceSolidity;
    private String contractAccount;
    private TransactionDecoderInterface decoder;
    private Integer informationSize;
    private Integer appealResultSize;

    private UserSaveDTO() {
    }

    public static UserSaveDTO getInstance() {
        return INSTANCE;
    }

    public ContractProxyService getItemTradeSolidity() {
        return contractTradeServiceSolidity;
    }

    public void setItemTradeSolidity(ContractProxyService contractTradeServiceSolidity) {
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
                ", identity='" + identity + '\'';
    }

    public String getContractAccount() {
        return contractAccount;
    }

    public void setContractAccount(String contractAccount) {
        this.contractAccount = contractAccount;
    }

    public TransactionDecoderInterface getDecoder() {
        return decoder;
    }

    public void setDecoder(TransactionDecoderInterface decoder) {
        this.decoder = decoder;
    }

    public Integer getInformationSize() {
        return informationSize;
    }

    public void setInformationSize(Integer informationSize) {
        this.informationSize = informationSize;
    }

    public Integer getAppealResultSize() {
        return appealResultSize;
    }

    public void setAppealResultSize(Integer appealResultSize) {
        this.appealResultSize = appealResultSize;
    }
}

