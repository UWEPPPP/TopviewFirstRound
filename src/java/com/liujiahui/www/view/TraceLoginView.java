package com.liujiahui.www.view;

import com.liujiahui.www.controller.TraceLoginController;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 登录
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceLoginView {
   static Scanner in = new Scanner(System.in);
    public void loginByConsumer() throws SQLException, IOException, ContractException, NoSuchAlgorithmException {
        System.out.println("进入消费者登录界面");
        System.out.println("请输入您的账号：");
        String account = in.next();
        System.out.println("请输入您的密码：");
        String password = in.next();
        TraceLoginController traceLoginController = new TraceLoginController();
        traceLoginController.login(account,password,false);
    }

    public void loginBySupplier() throws ContractException, SQLException, IOException, NoSuchAlgorithmException {
        System.out.println("进入供应商登录界面");
        System.out.println("请输入您的账号：");
        String account = in.next();
        System.out.println("请输入您的密码：");
        String password = in.next();
        TraceLoginController traceLoginController = new TraceLoginController();
        traceLoginController.login(account,password,true);
    }
    public static void loginReturnInterface() throws SQLException, IOException, ContractException, NoSuchAlgorithmException {
        System.out.println("登录失败,用户名或密码错误");
        System.out.println("即将返回主界面");
        TraceInitView.start();
    }
}
