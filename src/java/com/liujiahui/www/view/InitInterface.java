package com.liujiahui.www.view;

import com.liujiahui.www.controller.UserLoginController;
import com.liujiahui.www.controller.UserRegisterController;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 开始
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class InitInterface {
    public static void start() throws SQLException, IOException, ContractException {
        Scanner in = new Scanner(System.in);
        System.out.println("欢迎来到Topview集团旗下的产品溯源系统");
        System.out.println("1.登录");
        System.out.println("2.注册");
        System.out.println("3.退出");
        System.out.println("请输入你的选择：");
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                System.out.println("您是供应商还是消费者？(1 or 2)");
                int choice1 = in.nextInt();
                if (choice1 == 1 || choice1 == 2) {
                    new UserLoginController().loginOrderByIdentity(choice1);
                } else {
                    System.out.println("输入错误，请重新输入！");
                }
                break;
            case 2:
                System.out.println("您打算注册供应商账号还是消费者账号？(1 or 2)");
                int choice2 = in.nextInt();
                if (choice2 == 1 || choice2 == 2    ) {
                    new UserRegisterController().registerOrderByIdentity(choice2);
                } else {
                    System.out.println("输入错误，请重新输入！");
                }
                break;
            case 3:
                System.out.println("感谢您的使用，再见！");
                System.exit(0);
                break;
            default:
                System.out.println("输入错误，请重新输入！");
                break;
        }
    }
}
