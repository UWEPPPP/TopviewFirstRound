package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.impl.*;
import com.liujiahui.www.entity.bo.TraceLoginBO;
import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.UserPO;
import com.liujiahui.www.service.TraceRegisterAndLoginService;
import com.liujiahui.www.util.CryptoUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author 刘家辉
 * @date 2023/04/04
 */
public class TraceRegisterOrLoginServiceImpl implements TraceRegisterAndLoginService {

    private TraceRegisterOrLoginServiceImpl() {
    }

    public static TraceRegisterAndLoginService getInstance() {
        return RegisterOrLoginInstance.INSTANCE;
    }

    @Override
    public TraceInformationSaveDTO login(TraceLoginBO traceLoginBO) {
        String userAccount = traceLoginBO.getAccount();
        String userPassword = traceLoginBO.getPassword();
        String identity = traceLoginBO.getIdentity();
        String identityCheck = Objects.equals(identity, "consumer") ? "buyer_account" : "seller_account";
        String judge = "buyer_account".equals(identityCheck) ? "consumer_is_read" : "supplier_is_read";
        UserPO login;
        String inform = "suppliers";
        if (!Objects.equals(identity, inform)) {
            login = TraceFactoryDAO.getConsumerDAO().login(userAccount, userPassword);
        } else {
            login = TraceFactoryDAO.getSupplierDAO().login(userAccount, userPassword);
        }
        TraceInformationSaveDTO user = TraceInformationSaveDTO.getInstance();
        TraceContractServiceImpl traceContractService = TraceFactoryService.getTraceContractService();
        String balance;
        balance = String.valueOf(traceContractService.getBalance(login.getPrivateKey()));
        user.setUserName(login.getName());
        user.setBalance(balance);
        user.setGender(login.getGender());
        user.setPhone(login.getPhoneNumber());
        user.setIdentity(identity);
        user.setContractAccount(login.getAddress());
        if (identity.equals(inform)) {
            user.setInformationSize(new ConsumerFeedbackDAOImpl().getFeedbackNumber(login.getAddress()));
        }
        user.setAppealResultSize(new SupplierAppealDAOImpl().getResultAppealSize(login.getAddress(), identityCheck, judge));

        return user;
    }

    @Override
    public Boolean register(TraceRegisterBO traceRegisterBO) {
        String password = traceRegisterBO.getPassword();
        traceRegisterBO.setPassword(CryptoUtil.decryptHexPrivateKey(password, "src/resource/password.txt"));
        if (traceRegisterBO.getAddress() != null) {
            return new SupplierAccountDAOImpl().register(traceRegisterBO);
        } else {
            try {
                return new ConsumerAccountDAOImpl().register(traceRegisterBO);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private static class RegisterOrLoginInstance {
        private static final TraceRegisterOrLoginServiceImpl INSTANCE = new TraceRegisterOrLoginServiceImpl();
    }

}
