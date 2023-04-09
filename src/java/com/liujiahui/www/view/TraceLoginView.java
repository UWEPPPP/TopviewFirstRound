package com.liujiahui.www.view;

import com.liujiahui.www.controller.LoginAndRegisterController;

import java.util.Scanner;

/**
 * 登录
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceLoginView {
    static Scanner in = new Scanner(System.in);

    public static void loginReturnInterface() {
        System.out.println("登录失败,用户名或密码错误");
        System.out.println("即将返回主界面");
        TraceInitView.start();
    }

    public void login(Boolean choice) {
        if (choice) {
            System.out.println("进入供应商登录界面");
        } else {
            System.out.println("进入消费者登录界面");
        }
        System.out.println("请输入您的账号：");
        String account = in.next();
        System.out.println("请输入您的密码：");
        String password = in.next();
        String paddedStr = String.format("%-32s", password).replace(' ', '0');
        LoginAndRegisterController loginAndRegisterController = new LoginAndRegisterController();
        loginAndRegisterController.login(account, paddedStr, choice);
    }
}
