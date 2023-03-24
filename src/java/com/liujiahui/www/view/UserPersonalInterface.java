package com.liujiahui.www.view;

import com.liujiahui.www.controller.UserEntryController;
import com.liujiahui.www.entity.vo.UserDetailedVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 个人接口
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserPersonalInterface {
    static Scanner in = new Scanner(System.in);
    public static void showPersonalInterface(UserDetailedVO userDetailedVO) throws SQLException, IOException {
        System.out.println("用户名：" + userDetailedVO.getUserName());
        System.out.println("身份：" + userDetailedVO.getIdentify());
        System.out.println("性别：" + userDetailedVO.getGender());
        System.out.println("电话：" + userDetailedVO.getPhone());
        System.out.println("余额：" + userDetailedVO.getBalance());
        System.out.println("是否进行个人信息的修改？");
        System.out.println("1.修改用户名");
        System.out.println("2.修改性别");
        System.out.println("3.修改电话");
        System.out.println("4.否");
        int choice=in.nextInt();
        switch (choice){
            case 1:
                System.out.println("请输入新的用户名");

                break;
            case 2:
                System.out.println("请输入新的性别");
                break;
            case 3:
                System.out.println("请输入新的电话");
                break;
            case 4:
                break;
            default:
        }
        if(choice!=4){
            String change = in.next();
            UserEntryController userEntryController = new UserEntryController(true);
            userEntryController.changeUser(choice,change);
        }
        System.out.println("修改结束,是否继续修改？");
        System.out.println("1.是");
        System.out.println("2.否");
        int choice2=in.nextInt();
        switch (choice2){
            case 1:
                showPersonalInterface(userDetailedVO);
                break;
            case 2:
                break;
            default:
        }
    }
}
