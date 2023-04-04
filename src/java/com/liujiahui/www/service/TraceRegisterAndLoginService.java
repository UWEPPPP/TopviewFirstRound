package com.liujiahui.www.service;

import com.liujiahui.www.dao.TraceRegisterDAO;
import com.liujiahui.www.dao.impl.TraceFactoryDAO;
import com.liujiahui.www.entity.bo.TraceLoginBO;
import com.liujiahui.www.entity.bo.TraceRegisterBO;
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
    /**
     * @param traceLoginBO 用户登录博
     * @return {@link TraceInformationSaveDTO}
     * 用于用户登录
     */
    TraceInformationSaveDTO login(TraceLoginBO traceLoginBO) throws ContractException, SQLException, IOException;

    /**
     * @param traceRegisterBO 用户登记薄
     * @return {@link Boolean}
     * 用于用户注册
     */
    Boolean register(TraceRegisterBO traceRegisterBO) throws SQLException, IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
}
