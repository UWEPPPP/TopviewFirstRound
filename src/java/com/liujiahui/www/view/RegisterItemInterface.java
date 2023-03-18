package com.liujiahui.www.view;

import com.liujiahui.www.controller.UserEntryController;

import java.util.Scanner;

/**
 * 注册项接口
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class RegisterItemInterface {
    public static void registerItem(){
        Scanner in = new Scanner(System.in);
        System.out.println("请输入产品名称");
        String name = in.next();
        System.out.println("请输入产品价格");
        String price = in.next();
        System.out.println("请输入产品描述");
        String description = in.next();
        UserEntryController.registerItem(name,price,description);
    }
}
