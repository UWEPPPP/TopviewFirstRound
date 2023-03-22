package com.liujiahui.www.view;

import com.liujiahui.www.controller.UserEntryController;
import com.liujiahui.www.entity.vo.UserAfterLoginVO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 消费者主界面
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class UserMainInterface {
    static Scanner in = new Scanner(System.in);
    public static void viewConsumer(UserAfterLoginVO userAfterLoginVO) throws ContractException, SQLException, IOException, NoSuchAlgorithmException {
        if(userAfterLoginVO!=null){
            do {
                System.out.println("您好消费者" + userAfterLoginVO.getName() + "欢迎您来到Topview的产品溯源系统");
                System.out.println("您的余额为" + userAfterLoginVO.getBalance());
                System.out.println("请选择您要进行的操作");
                System.out.println("1.查看产品信息");
                System.out.println("2.查看个人信息");
                System.out.println("3.查看我购买的产品");
            } while (mainInterface());
            UserInitInterface.start();
    }else {
            System.out.println("用户名或者密码错误");
            System.out.println("即将返回登录界面");
            UserInitInterface.start();
        }
    }

    private static boolean mainInterface() throws SQLException, IOException, ContractException, NoSuchAlgorithmException {
        System.out.println("0.退出登录");
        int choice = in.nextInt();
        if(choice!=0){
            UserEntryController.entry(choice);
        }else {
            System.out.println("退出登录成功");
            System.out.println("即将返回登录界面");
            return false;
        }
        return true;
    }

    public static void viewSupplier(UserAfterLoginVO userAfterLoginVO) throws ContractException, SQLException, IOException, NoSuchAlgorithmException {
        if(userAfterLoginVO!=null){
            System.out.println("您好 供应商" + userAfterLoginVO.getName() + "欢迎您来到Topview的产品溯源系统");
            do {
                System.out.println("您的余额为" + userAfterLoginVO.getBalance());
                System.out.println("请选择您要进行的操作");
                System.out.println("1.查看产品信息");
                System.out.println("2.查看个人信息");
                System.out.println("3.产品上新");
                System.out.println("4.进入我的产品主页");
            } while (mainInterface());
            UserInitInterface.start();
        }else {
            System.out.println("用户名或者密码错误");
            System.out.println("即将返回登录界面");
            UserInitInterface.start();
        }
    }
}
