package com.liujiahui.www.view;

import com.liujiahui.www.controller.UserBuyController;
import com.liujiahui.www.controller.UserEntryController;
import com.liujiahui.www.entity.po.Item;
import com.liujiahui.www.entity.vo.TranscationVO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
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
        in.nextLine();
        //读取换行符
        System.out.println("请输入商品描述");
        String description = in.nextLine();
        System.out.println("--模拟商品真实属性--");
        System.out.println("如果商品为正品，请保证以上信息与商品真实信息一致");
        System.out.println("请输入商品真实名称");
        String realName = in.nextLine();
        System.out.println("请输入商品真实描述");
        String realDescription = in.nextLine();
        UserEntryController.registerItem(name,price,description,realName,realDescription);
        System.out.println("商品上架成功");
    }

    public static void showAndBuyItem (List<Item> items) throws SQLException, IOException, ContractException {
        System.out.println("商品列表");
        for (Item item : items) {
            if(!item.getSold()){
            System.out.println(item.getId()+" "+"商品名称：" + item.getName() + " 商品价格：" + item.getPrice() + " 商品描述：" + item.getDescription()+"商品所有人 "+item.getOwner());
        }
        }
        System.out.println("是否要购买商品？");
        System.out.println("1.是");
        System.out.println("2.否");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                System.out.println("请输入商品id");
                int id = in.nextInt();
                UserBuyController.buy(id,items);
                break;
            case 2:
                break;
            default:
        }
    }

    public static void showMyItem(List<Item> items) throws SQLException, IOException, ContractException {
        System.out.println("这是您已购入的商品列表");
        for (Item item : items) {
            if(!item.getSold()){
                System.out.println(item.getId()+" "+"商品名称：" + item.getName() + " 商品价格：" + item.getPrice() + " 商品描述：" + item.getDescription()+"商品所有人"+item.getOwner());
            }
        }
        System.out.println("是否按照hash给商品验伪？");
        System.out.println("1.是");
        System.out.println("2.否");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                System.out.println("请输入商品hash");
                String hash = in.next();
                TranscationVO check = UserBuyController.check(hash);
                System.out.println("根据hash "+check.getHash()+"查到的产品信息是");
                System.out.println(check.getName());
                System.out.println(check.getDescription());
                break;
            case 2:
                break;
            default:
        }
    }
    public static void showSoldItem(List<Item> items) throws SQLException, IOException, ContractException {
        System.out.println("商品列表");
        for (Item item : items) {
            if(item.getSold()){
                System.out.println(item.getId()+" "+"商品名称：" + item.getName() + " 商品价格：" + item.getPrice() + " 商品描述：" + item.getDescription());
            }
        }
        System.out.println("你要更新它们的物流情况(商品状态)吗？");
        System.out.println("1.是");
        System.out.println("2.否");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                System.out.println("请输入商品id");
                int id = in.nextInt();
                System.out.println("请输入物流信息");
                String logistics = in.next();
                UserBuyController.updateLogistics(id,logistics);
                break;
            case 2:
                break;
            default:
        }
    }
    public static void showResult(TranscationVO transcationVO) {
        System.out.println("交易结果");
        Date date = new Date();
        System.out.println("交易时间："+date);
        System.out.println("交易买家："+transcationVO.getBuyer());
        System.out.println("交易卖家："+transcationVO.getSeller());
        System.out.println("交易商品："+transcationVO.getName());
        System.out.println("交易价格："+transcationVO.getPrice());
        System.out.println("交易验伪hash："+transcationVO.getHash());
        System.out.println("交易后余额："+transcationVO.getBalance());
    }
}
