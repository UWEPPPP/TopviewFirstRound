package com.liujiahui.www.view;

import com.liujiahui.www.controller.UserEntryByConsumer;
import com.liujiahui.www.entity.vo.UserAfterLoginVO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 消费者主界面
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class ConsumerMainInterface {
    static Scanner in = new Scanner(System.in);
    public static void view(UserAfterLoginVO userAfterLoginVO) throws ContractException, SQLException, IOException {
        if(userAfterLoginVO!=null){
            do{
        System.out.println("您好消费者"+userAfterLoginVO.getName()+"欢迎您来到Topview的产品溯源系统");
        System.out.println("您的余额为"+userAfterLoginVO.getBalance());
        System.out.println("请选择您要进行的操作");
        System.out.println("1.查看产品信息");
        System.out.println("2.查看个人信息");
        int choice=in.nextInt();
        UserEntryByConsumer.entry(choice);
            }while (true);
    }else {
            System.out.println("用户名或者密码错误");
            System.out.println("即将返回登录界面");
            InitInterface.start();
        }
    }
}
