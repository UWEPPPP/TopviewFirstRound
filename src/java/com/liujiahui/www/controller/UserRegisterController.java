package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.UserRegisterBO;
import com.liujiahui.www.entity.dto.TraceRegisterDTO;
import com.liujiahui.www.service.TraceFactoryService;
import com.liujiahui.www.service.TraceRegisterAndLoginService;
import com.liujiahui.www.service.impl.TraceRegisterAndLoginByConsumerServiceImpl;
import com.liujiahui.www.service.impl.TraceRegisterAndLoginBySupplierServiceImpl;
import com.liujiahui.www.view.UserRegisterInterface;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * 用户注册控制器
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class UserRegisterController {
    public void register(TraceRegisterDTO traceRegisterDTO) throws SQLException, IOException, ContractException, NoSuchAlgorithmException {
        UserRegisterBO userRegisterBO = new UserRegisterBO();
        userRegisterBO.setName(traceRegisterDTO.getName());
        userRegisterBO.setGender(traceRegisterDTO.getGender());
        userRegisterBO.setPhone(traceRegisterDTO.getPhone());
        userRegisterBO.setPassword(traceRegisterDTO.getPassword());
        userRegisterBO.setAddress(traceRegisterDTO.getAddress());
        TraceRegisterAndLoginService traceRegisterAndLoginService = TraceFactoryService.getTraceRegisterAndLoginService(traceRegisterDTO.getChoice());
        UserRegisterInterface.returnInterface(traceRegisterAndLoginService.register(userRegisterBO));
    }
    public void registerOrderByIdentity(int choice2) throws SQLException, IOException, ContractException, NoSuchAlgorithmException {
        if(choice2 == 1){
            new UserRegisterInterface().registerBySupplier();
    }else {
            new UserRegisterInterface().registerByConsumer();
        }
    }
}
