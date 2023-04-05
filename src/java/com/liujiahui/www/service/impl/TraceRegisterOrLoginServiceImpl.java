package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.impl.ConsumerAccountDAOImpl;
import com.liujiahui.www.dao.impl.ConsumerFeedbackDAOImpl;
import com.liujiahui.www.dao.impl.SupplierAppealDAOImpl;
import com.liujiahui.www.dao.impl.SupplierAccountDAOImpl;
import com.liujiahui.www.entity.bo.TraceLoginBO;
import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.UserPO;
import com.liujiahui.www.service.TraceRegisterAndLoginService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author 刘家辉
 * @date 2023/04/04
 */
public class TraceRegisterOrLoginServiceImpl implements TraceRegisterAndLoginService {

    private static class RegisterOrLoginInstance {
        private static final TraceRegisterOrLoginServiceImpl INSTANCE = new TraceRegisterOrLoginServiceImpl();
    }

    private TraceRegisterOrLoginServiceImpl() {
    }

    public static TraceRegisterAndLoginService getInstance() {
        return RegisterOrLoginInstance.INSTANCE;
    }

    @Override
    public TraceInformationSaveDTO login(TraceLoginBO traceLoginBO) throws ContractException {
        String userAccount = traceLoginBO.getAccount();
        String userPassword = traceLoginBO.getPassword();
        String identity = traceLoginBO.getIdentity();
        String identityCheck = Objects.equals(identity, "consumer") ? "buyer_account" : "seller_account";
        String judge = "buyer_account".equals(identityCheck) ? "consumer_is_read" : "supplier_is_read";
        UserPO login;
        String inform = "suppliers";
        if(!Objects.equals(identity, inform)){
            login = new ConsumerAccountDAOImpl().login(userAccount, userPassword);
        }else {
            login = new SupplierAccountDAOImpl().login(userAccount, userPassword);
        }
        TraceInformationSaveDTO user = TraceInformationSaveDTO.getInstance();
        TraceContractServiceImpl traceContractService = TraceFactoryService.getTraceContractService();
        String balance;
        try {
            balance = String.valueOf(traceContractService.getBalance(login.getPrivateKey()));
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException e) {
            System.out.println("获取余额失败");
            throw new RuntimeException(e);
        }
        user.setUserName(login.getName());
        user.setBalance(balance);
        user.setGender(login.getGender());
        user.setPhone(login.getPhoneNumber());
        user.setIdentity(identity);
        user.setContractAccount(login.getAddress());
        if (identity.equals(inform)) {
            user.setInformationSize(new ConsumerFeedbackDAOImpl().getFeedbackNumber(login.getAddress()));
        }
        user.setAppealResultSize(new SupplierAppealDAOImpl().getResultAppealSize(login.getAddress(),identityCheck,judge));

        return user;
    }
    @Override
    public Boolean register(TraceRegisterBO traceRegisterBO) {
        if(traceRegisterBO.getAddress()!=null){
           return new SupplierAccountDAOImpl().register(traceRegisterBO);
        }else {
            return new ConsumerAccountDAOImpl().register(traceRegisterBO);
        }
    }

}
