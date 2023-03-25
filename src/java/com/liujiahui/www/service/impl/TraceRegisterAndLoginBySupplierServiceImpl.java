package com.liujiahui.www.service.impl;

import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.po.TraceSupplierPO;
import com.liujiahui.www.service.TraceRegisterAndLoginService;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceRegisterAndLoginBySupplierServiceImpl implements TraceRegisterAndLoginService {
    private static final TraceRegisterAndLoginBySupplierServiceImpl SERVICE = new TraceRegisterAndLoginBySupplierServiceImpl();
    private TraceRegisterAndLoginBySupplierServiceImpl() {
    }
    public static TraceRegisterAndLoginBySupplierServiceImpl getInstance() {
        return SERVICE;
    }

    @Override
    public Boolean register(TraceRegisterBO traceRegisterBO) throws SQLException, IOException {
        TraceSupplierPO traceSupplierPO = new TraceSupplierPO();
        traceSupplierPO.setName(traceRegisterBO.getName());
        traceSupplierPO.setGender(traceRegisterBO.getGender());
        traceSupplierPO.setPhoneNumber(traceRegisterBO.getPhone());
        traceSupplierPO.setPassword(traceRegisterBO.getPassword());
        traceSupplierPO.setAddress(traceRegisterBO.getAddress());
        return TRACE_REGISTER_DAO.register(traceSupplierPO);
    }


}
