package com.liujiahui.www.service;

import com.liujiahui.www.dao.TraceRegisterDAO;
import com.liujiahui.www.dao.impl.TraceFactoryDAO;
import com.liujiahui.www.dao.impl.TraceLoginDAOImpl;
import com.liujiahui.www.entity.bo.TraceLoginBO;
import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.dto.TraceAccountOnJavaDTO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * 用户注册和登录服务
 *
 * @author 刘家辉
 * @date 2023/03/24
 */
public interface TraceRegisterAndLoginService {
    TraceRegisterDAO TRACE_REGISTER_DAO = TraceFactoryDAO.getTraceRegisterDAO();
    TraceLoginDAOImpl TRACE_LOGIN_DAO = TraceFactoryDAO.getTraceLoginDAO();

    /**
     * @param traceRegisterBO 用户登记薄
     * @return {@link Boolean}
     * 用于用户注册
     */
    Boolean register(TraceRegisterBO traceRegisterBO) throws SQLException, IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    /**
     * @param traceLoginBO 用户登录博
     * @return {@link TraceInformationSaveDTO}
     * 用于用户登录
     */
    static TraceInformationSaveDTO login(TraceLoginBO traceLoginBO) throws ContractException, SQLException, IOException{
        String account = traceLoginBO.getAccount();
        String password = traceLoginBO.getPassword();
        String identity = traceLoginBO.getIdentity();
        TraceAccountOnJavaDTO traceAccountOnJavaDTO = new TraceAccountOnJavaDTO();
        traceAccountOnJavaDTO.setAccount(account);
        traceAccountOnJavaDTO.setPassword(password);
        traceAccountOnJavaDTO.setIdentity(identity);
        return TRACE_LOGIN_DAO.login(traceAccountOnJavaDTO);
    }
}
