package com.liujiahui.www.service.impl;

import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.po.TraceConsumerPO;
import com.liujiahui.www.service.TraceRegisterAndLoginService;

import java.io.IOException;
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
    public Boolean register(TraceRegisterBO traceRegisterBO) throws SQLException, IOException {
        TraceConsumerPO traceConsumerPO = new TraceConsumerPO();
        traceConsumerPO.setName(traceRegisterBO.getName());
        traceConsumerPO.setGender(traceRegisterBO.getGender());
        traceConsumerPO.setPhoneNumber(traceRegisterBO.getPhone());
        traceConsumerPO.setPassword(traceRegisterBO.getPassword());
        return TRACE_REGISTER_DAO.register(traceConsumerPO);
    }


}
