package com.liujiahui.www.view;

import com.liujiahui.www.controller.UserLoginController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 登录
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class LoginInterface {
   static Scanner in = new Scanner(System.in);
    public void loginByConsumer() throws SQLException, IOException {
        System.out.println("进入消费者登录界面");
        System.out.println("请输入您的账号：");
        int account = in.nextInt();
        System.out.println("请输入您的密码：");
        String password = in.next();
        UserLoginController.loginByConsumer(account,password);
    }

    public void loginBySupplier() {
        System.out.println("进入供应商登录界面");
        System.out.println("请输入您的账号：");
        int account = in.nextInt();
        System.out.println("请输入您的密码：");
        String password = in.next();
        UserLoginController.loginBySupplier(account,password);
    }
}
