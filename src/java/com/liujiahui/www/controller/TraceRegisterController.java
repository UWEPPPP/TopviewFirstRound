package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.dto.TraceRegisterDTO;
import com.liujiahui.www.service.TraceRegisterAndLoginService;
import com.liujiahui.www.service.impl.TraceFactoryImplService;
import com.liujiahui.www.view.TraceRegisterView;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * 用户注册控制器
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceRegisterController {
    public void register(TraceRegisterDTO traceRegisterDTO) throws Exception {
        TraceRegisterBO traceRegisterBO = new TraceRegisterBO();
        traceRegisterBO.setName(traceRegisterDTO.getName());
        traceRegisterBO.setGender(traceRegisterDTO.getGender());
        traceRegisterBO.setPhone(traceRegisterDTO.getPhone());
        traceRegisterBO.setPassword(traceRegisterDTO.getPassword());
        traceRegisterBO.setAddress(traceRegisterDTO.getAddress());
        TraceRegisterAndLoginService traceRegisterAndLoginService = TraceFactoryImplService.getTraceRegisterAndLoginService(traceRegisterDTO.getChoice());
        TraceRegisterView.returnInterface(traceRegisterAndLoginService.register(traceRegisterBO));
    }

    public void registerOrderByIdentity(int choice2) throws Exception {
        if (choice2 == 1) {
            new TraceRegisterView().registerBySupplier();
        } else {
            new TraceRegisterView().registerByConsumer();
        }
    }
}
