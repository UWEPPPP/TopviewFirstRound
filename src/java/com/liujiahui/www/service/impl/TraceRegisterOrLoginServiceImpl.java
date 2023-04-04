package com.liujiahui.www.service.impl;

import com.liujiahui.www.entity.bo.TraceLoginBO;
import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.dto.TraceAccountOnJavaDTO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceSupplierPO;
import com.liujiahui.www.service.TraceRegisterAndLoginService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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
    public TraceInformationSaveDTO login(TraceLoginBO traceLoginBO) throws ContractException, SQLException, IOException {
        String account = traceLoginBO.getAccount();
        String password = traceLoginBO.getPassword();
        String identity = traceLoginBO.getIdentity();
        TraceAccountOnJavaDTO traceAccountOnJavaDTO = new TraceAccountOnJavaDTO();
        traceAccountOnJavaDTO.setAccount(account);
        traceAccountOnJavaDTO.setPassword(password);
        traceAccountOnJavaDTO.setIdentity(identity);
        return TRACE_LOGIN_DAO.login(traceAccountOnJavaDTO);
    }

    @Override
    public Boolean register(TraceRegisterBO traceRegisterBO) throws SQLException, IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        TraceSupplierPO traceSupplier = new TraceSupplierPO();
        traceSupplier.setName(traceRegisterBO.getName());
        traceSupplier.setGender(traceRegisterBO.getGender());
        traceSupplier.setPhoneNumber(traceRegisterBO.getPhone());
        traceSupplier.setPassword(traceRegisterBO.getPassword());
        if (traceRegisterBO.getAddress() != null) {
            traceSupplier.setAddress(traceRegisterBO.getAddress());
        }
        return TRACE_REGISTER_DAO.register(traceSupplier);
    }

    private static class RegisterOrLoginInstance {
        private static final TraceRegisterOrLoginServiceImpl INSTANCE = new TraceRegisterOrLoginServiceImpl();
    }
}
