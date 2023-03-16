package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.UserRegisterBO;
import com.liujiahui.www.service.UserRegisterService;
import com.liujiahui.www.view.InitInterface;
import com.liujiahui.www.view.RegisterInterface;
import com.liujiahui.www.view.ReturnInterface;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 用户注册控制器
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class UserRegisterController {
    public static void registerBySupplier(String name, String gender, int phone, String address, String password) throws SQLException, IOException {
        UserRegisterBO userRegisterBO = new UserRegisterBO();
        userRegisterBO.setName(name);
        userRegisterBO.setGender(gender);
        userRegisterBO.setPhone(phone);
        userRegisterBO.setAddress(address);
        userRegisterBO.setPassword(password);
        ReturnInterface.returnInterface(UserRegisterService.registerBySupplier(userRegisterBO));
    }

    public static void registerByConsumer(String name, String gender, int phone, String password) throws SQLException, IOException {
        UserRegisterBO userRegisterBO = new UserRegisterBO();
        userRegisterBO.setName(name);
        userRegisterBO.setPassword(password);
        userRegisterBO.setGender(gender);
        userRegisterBO.setPhone(phone);
        ReturnInterface.returnInterface(UserRegisterService.registerByConsumer(userRegisterBO));
    }

    public void registerOrderByIdentity(int choice2) throws SQLException, IOException {
        if(choice2 == 1){
            new RegisterInterface().registerBySupplier();
    }else {
            new RegisterInterface().registerByConsumer();
        }
    }
}
