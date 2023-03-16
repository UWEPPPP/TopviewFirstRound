package com.liujiahui.www.view;

import com.liujiahui.www.controller.UserRegisterController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 注册界面
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class RegisterInterface {
    static Scanner in = new Scanner(System.in);
    public void registerByConsumer() throws SQLException, IOException {
        System.out.println("进入消费者注册界面");
        System.out.println("请输入您的姓名：");
        String name = in.next();
        System.out.println("请输入您的性别：(m or w)");
        String gender = in.next();
        System.out.println("请输入您的电话：");
        int phone = in.nextInt();
        System.out.println("请输入您要设置的密码：");
        String password = in.next();
        UserRegisterController.registerByConsumer(name,gender,phone,password);
    }

    public void registerBySupplier() throws SQLException, IOException {
        System.out.println("进入供应商注册界面");
        System.out.println("请输入您的姓名：");
        String name = in.next();
        System.out.println("请输入您的性别：(m or w)");
        String gender = in.next();
        System.out.println("请输入您的电话：");
        int phone = in.nextInt();
        System.out.println("请输入您的地址：");
        String address = in.next();
        System.out.println("请输入您要设置的密码：");
        String password = in.next();
        UserRegisterController.registerBySupplier(name,gender,phone,address,password);
    }
}
