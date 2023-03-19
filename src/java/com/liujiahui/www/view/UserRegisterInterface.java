package com.liujiahui.www.view;

import com.liujiahui.www.controller.UserRegisterController;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 注册界面
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class UserRegisterInterface {
    static Scanner in = new Scanner(System.in);
    public void registerByConsumer() throws SQLException, IOException, ContractException {
        System.out.println("进入消费者注册界面");
        System.out.println("请输入您的姓名：");
        String name = in.next();
        System.out.println("请输入您的性别：(m or w)");
        String gender = in.next();
        System.out.println("请输入您的电话：");
        String phone = in.next();
        System.out.println("请输入您要设置的密码：");
        String password = in.next();
        UserRegisterController.registerByConsumer(name,gender,phone,password);
    }

    public void registerBySupplier() throws SQLException, IOException, ContractException {
        System.out.println("进入供应商注册界面");
        System.out.println("请输入您的姓名：");
        String name = in.next();
        System.out.println("请输入您的性别：(m or w)");
        String gender = in.next();
        System.out.println("请输入您的电话：");
        String phone = in.next();
        System.out.println("请输入您的地址：");
        String address = in.next();
        System.out.println("请输入您要设置的密码：");
        String password = in.next();
        UserRegisterController.registerBySupplier(name,gender,phone,address,password);
    }
    public static void returnInterface(Boolean bool) throws SQLException, IOException, ContractException {
        if(bool){
            System.out.println("注册成功");
            System.out.println("登录时账号即为您输入的名字"+" ,密码为你设置的密码 "+" ,账号初始余额为1000");
            System.out.println("即将返回主界面");
        }else {
            System.out.println("注册失败,用户名已存在");
            System.out.println("即将返回主界面");
        }
        UserInitInterface.start();
    }
}