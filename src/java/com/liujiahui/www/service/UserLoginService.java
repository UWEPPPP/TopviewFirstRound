package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserDAO;
import com.liujiahui.www.entity.bo.UserLoginBO;
import com.liujiahui.www.entity.bo.UserRegisterBO;
import com.liujiahui.www.entity.po.Consumer;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;

import java.io.IOException;
import java.sql.SQLException;

public class UserLoginService {
    public static void loginByConsumer(UserLoginBO userLoginBO) throws SQLException, IOException {
        int account = userLoginBO.getAccount();
        String password = userLoginBO.getPassword();
        Consumer consumer = new Consumer();
        consumer.setPhoneNumber(account);
        consumer.setPassword(password);
        UserDAO userDAO = new UserDAO();
       Consumer consumer1=(Consumer)userDAO.login(consumer);
        if(consumer1!=null){
           System.out.println("登录成功");
    }
    }

    public static void loginBySupplier(UserLoginBO userLoginBO) {
    }

    public static void registerByConsumer(UserRegisterBO userRegisterBO) {
       // 创建非国密类型的CryptoSuite
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
      // 随机生成非国密公私钥对
        CryptoKeyPair cryptoKeyPair = cryptoSuite.createKeyPair();
      // 获取账户地址
        String accountAddress = cryptoKeyPair.getAddress();
        cryptoKeyPair.getHexPrivateKey();
    }
}
