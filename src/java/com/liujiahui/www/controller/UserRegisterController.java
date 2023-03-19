package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.UserRegisterBO;
import com.liujiahui.www.service.UserRegisterAndLoginService;
import com.liujiahui.www.view.UserRegisterInterface;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 用户注册控制器
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class UserRegisterController {
    public static void registerBySupplier(String name, String gender, String phone, String address, String password) throws SQLException, IOException, ContractException {
        UserRegisterBO userRegisterBO = new UserRegisterBO();
        userRegisterBO.setName(name);
        userRegisterBO.setGender(gender);
        userRegisterBO.setPhone(phone);
        userRegisterBO.setAddress(address);
        userRegisterBO.setPassword(password);
        UserRegisterInterface.returnInterface(UserRegisterAndLoginService.registerBySupplier(userRegisterBO));
    }

    public static void registerByConsumer(String name, String gender, String phone, String password) throws SQLException, IOException, ContractException {
        UserRegisterBO userRegisterBO = new UserRegisterBO();
        userRegisterBO.setName(name);
        userRegisterBO.setPassword(password);
        userRegisterBO.setGender(gender);
        userRegisterBO.setPhone(phone);
        UserRegisterInterface.returnInterface(UserRegisterAndLoginService.registerByConsumer(userRegisterBO));
    }

    public void registerOrderByIdentity(int choice2) throws SQLException, IOException, ContractException {
        if(choice2 == 1){
            new UserRegisterInterface().registerBySupplier();
    }else {
            new UserRegisterInterface().registerByConsumer();
        }
    }
}
