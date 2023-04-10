package com.liujiahui.www.view;

import com.liujiahui.www.controller.LoginAndRegisterController;
import com.liujiahui.www.entity.dto.TraceRegisterDTO;

import java.util.Scanner;

/**
 * 注册界面
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceRegisterView {
    static Scanner in = new Scanner(System.in);

    public static void returnInterface(Boolean bool) {
        if (bool) {
            System.out.println("注册成功");
            System.out.println("登录时账号即为您输入的名字" + " ,密码为你设置的密码 " + " ,账号初始余额为1000");
            System.out.println("即将返回主界面");
        } else {
            System.out.println("注册失败,用户名已存在");
            System.out.println("即将返回主界面");
        }
        TraceInitView.start();
    }

    public void registerByConsumer() {
        System.out.println("进入消费者注册界面");
        System.out.println("请输入您的姓名：");
        String name = in.next();
        System.out.println("请输入您的性别：(m or w)");
        String gender = in.next();
        System.out.println("请输入您的电话：");
        String phone = in.next();
        TraceRegisterDTO traceRegisterDTO = showRegister(name, gender, phone);
        traceRegisterDTO.setChoice(false);
        new LoginAndRegisterController().register(traceRegisterDTO);
    }

    private TraceRegisterDTO showRegister(String name, String gender, String phone) {
        System.out.println("请输入您要设置的密码：(4-12位)");
        String password = in.next();
        String paddedStr = String.format("%-32s", password).replace(' ', '0');
        TraceRegisterDTO traceRegisterDTO = new TraceRegisterDTO();
        traceRegisterDTO.setName(name);
        traceRegisterDTO.setGender(gender);
        traceRegisterDTO.setPhone(phone);
        traceRegisterDTO.setPassword(paddedStr);
        return traceRegisterDTO;
    }

    public void registerBySupplier() {
        System.out.println("进入供应商注册界面");
        System.out.println("请输入您的姓名：");
        String name = in.next();
        System.out.println("请输入您的性别：(m or w)");
        String gender = in.next();
        System.out.println("请输入您的电话：");
        String phone = in.next();
        System.out.println("请输入您的地址：");
        String address = in.next();
        TraceRegisterDTO traceRegisterDTO = showRegister(name, gender, phone);
        traceRegisterDTO.setAddress(address);
        traceRegisterDTO.setChoice(true);
        new LoginAndRegisterController().register(traceRegisterDTO);
    }
}
