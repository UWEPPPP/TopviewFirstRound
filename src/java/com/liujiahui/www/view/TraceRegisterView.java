package com.liujiahui.www.view;

import com.liujiahui.www.controller.TraceRegisterController;
import com.liujiahui.www.entity.dto.TraceRegisterDTO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 注册界面
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceRegisterView {
    static Scanner in = new Scanner(System.in);

    public static void returnInterface(Boolean bool) throws Exception {
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

    public void registerByConsumer() throws Exception {
        System.out.println("进入消费者注册界面");
        System.out.println("请输入您的姓名：");
        String name = in.next();
        System.out.println("请输入您的性别：(m or w)");
        String gender = in.next();
        System.out.println("请输入您的电话：");
        String phone = in.next();
        TraceRegisterDTO traceRegisterDTO = showRegister(name, gender, phone);
        traceRegisterDTO.setChoice(false);
        TraceRegisterController traceRegisterController = new TraceRegisterController();
        traceRegisterController.register(traceRegisterDTO);
    }

    private TraceRegisterDTO showRegister(String name, String gender, String phone) {
        System.out.println("请输入您要设置的密码：");
        String password = in.next();
        TraceRegisterDTO traceRegisterDTO = new TraceRegisterDTO();
        traceRegisterDTO.setName(name);
        traceRegisterDTO.setGender(gender);
        traceRegisterDTO.setPhone(phone);
        traceRegisterDTO.setPassword(password);
        return traceRegisterDTO;
    }

    public void registerBySupplier() throws Exception {
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
        TraceRegisterController traceRegisterController = new TraceRegisterController();
        traceRegisterController.register(traceRegisterDTO);
    }
}
