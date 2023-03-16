package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.UserLoginBO;
import com.liujiahui.www.service.UserLoginService;
import com.liujiahui.www.view.LoginInterface;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 用户登录控制器
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class UserLoginController {
    public static void loginByConsumer(int account, String password) throws SQLException, IOException {
        UserLoginBO userLoginBO = new UserLoginBO();
        userLoginBO.setAccount(account);
        userLoginBO.setPassword(String.valueOf(password));
         UserLoginService.loginByConsumer(userLoginBO);
    }

    public static void loginBySupplier(int account, String password) {
        UserLoginBO userLoginBO = new UserLoginBO();
        userLoginBO.setAccount(account);
        userLoginBO.setPassword(String.valueOf(password));
        UserLoginService.loginBySupplier(userLoginBO);
    }

    /**
     * 登录
     */
    public void loginOrderByIdentity(int choice1) throws SQLException, IOException {
        if(choice1 == 1){
            new LoginInterface().loginBySupplier();
        }else {
            new LoginInterface().loginByConsumer();
        }
    }

}
