package com.liujiahui.www.service.impl;

import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.po.TraceConsumerPO;
import com.liujiahui.www.service.TraceRegisterAndLoginService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * 用户注册服务
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceRegisterAndLoginByConsumerServiceImpl implements TraceRegisterAndLoginService {
    private static final TraceRegisterAndLoginByConsumerServiceImpl SERVICE = new TraceRegisterAndLoginByConsumerServiceImpl();
    private TraceRegisterAndLoginByConsumerServiceImpl() {
    }
    public static TraceRegisterAndLoginByConsumerServiceImpl getInstance() {
        return SERVICE;
    }
     @Override
    public Boolean register(TraceRegisterBO traceRegisterBO) throws SQLException, IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        TraceConsumerPO traceConsumer = new TraceConsumerPO();
        traceConsumer.setName(traceRegisterBO.getName());
        traceConsumer.setGender(traceRegisterBO.getGender());
        traceConsumer.setPhoneNumber(traceRegisterBO.getPhone());
        traceConsumer.setPassword(traceRegisterBO.getPassword());
        return TRACE_REGISTER_DAO.register(traceConsumer);
    }


}
