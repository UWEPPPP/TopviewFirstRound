package com.liujiahui.www.view;

import com.liujiahui.www.service.UserRegisterService;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 返回界面
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class ReturnInterface {
    public static void returnInterface(Boolean bool) throws SQLException, IOException {
        if(bool){
            System.out.println("注册成功");
            System.out.println("登录时账号即为您输入的名字"+" ,密码为你设置的密码 "+" ,账号初始余额为1000");
            System.out.println("即将返回主界面");
        }else {
            System.out.println("注册失败,用户名已存在");
            System.out.println("即将返回主界面");
        }
        InitInterface.start();
    }
}
