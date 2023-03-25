package com.liujiahui.www.service.impl;

import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.po.TraceSupplierPO;
import com.liujiahui.www.service.TraceRegisterAndLoginService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    public Boolean register(TraceRegisterBO traceRegisterBO) throws SQLException, IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        TraceSupplierPO traceSupplier = new TraceSupplierPO();
        traceSupplier.setName(traceRegisterBO.getName());
        traceSupplier.setGender(traceRegisterBO.getGender());
        traceSupplier.setPhoneNumber(traceRegisterBO.getPhone());
        traceSupplier.setPassword(traceRegisterBO.getPassword());
        traceSupplier.setAddress(traceRegisterBO.getAddress());
        return TRACE_REGISTER_DAO.register(traceSupplier);
    }


}
