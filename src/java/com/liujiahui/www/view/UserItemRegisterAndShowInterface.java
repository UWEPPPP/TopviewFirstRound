package com.liujiahui.www.view;

import com.liujiahui.www.controller.UserEntryController;
import com.liujiahui.www.entity.po.Item;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * 注册项接口
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserItemRegisterAndShowInterface {
    public static void registerItem() throws SQLException, IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入产品名称");
        String name = in.next();
        System.out.println("请输入产品价格");
        BigInteger price = in.nextBigInteger();
        System.out.println("请输入商品描述");
        String description = in.next();
        System.out.println("--模拟商品真实属性--");
        System.out.println("如果商品为正品，请保证以上信息与商品真实信息一致");
        System.out.println("请输入商品真实名称");
        String realName = in.next();
        System.out.println("请输入商品真实描述");
        String realDescription = in.next();
        UserEntryController.registerItem(name,price,description,realName,realDescription);
        System.out.println("商品上架成功");
    }

    public static void showItem(List<Item> items) {
        System.out.println("商品列表");
        for (Item item : items) {
            System.out.println(item.getId()+" "+"商品名称：" + item.getName() + "商品价格：" + item.getPrice() + "商品描述：" + item.getDescription());
        }
    }
}
