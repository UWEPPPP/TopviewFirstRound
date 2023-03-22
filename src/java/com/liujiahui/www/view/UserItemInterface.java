package com.liujiahui.www.view;

import com.liujiahui.www.controller.UserBuyController;
import com.liujiahui.www.controller.UserEntryController;
import com.liujiahui.www.entity.po.Item;
import com.liujiahui.www.entity.vo.UserItemStatusVO;
import com.liujiahui.www.entity.vo.UserTransactionVO;
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
public class UserItemInterface {
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
            if(item.getSold()){
            System.out.println(item.getId()+" "+"商品名称：" + item.getName() + " 商品价格：" + item.getPrice() + " 商品描述：" + item.getDescription()+" 已售出 ");
        }else {
                System.out.println(item.getId()+" "+"商品名称：" + item.getName() + " 商品价格：" + item.getPrice() + " 商品描述：" + item.getDescription()+" 未售出 卖家："+item.getOwnerName());
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

    public static void showMyItem(List<Item> items) throws ContractException {
        System.out.println("这是您已购入的商品列表");
        for (Item item : items) {
                System.out.println(item.getId()+" "+"商品名称：" + item.getName() + " 商品价格：" + item.getPrice() + " 商品描述:" + item.getDescription()+" 商品hash:"+item.getHashes());
        }
        System.out.println("1.通过产品购买时给予的hash值验伪");
        System.out.println("2.查看产品运送状态");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                System.out.println("请输入商品hash");
                String hash = in.next();
                UserTransactionVO check = UserBuyController.check(hash);
                System.out.println("根据hash "+check.getHash()+"查到的产品信息是");
                System.out.println("名字"+check.getName());
                System.out.println("详情"+check.getDescription());
                System.out.println("信息是否如购买时商家所给？");
                System.out.println("1.是");
                System.out.println("2.否");
                int choice1 = in.nextInt();
                switch (choice1) {
                    case 1:
                        System.out.println("坚定为真品，是否给予商家好评？");
                        System.out.println("1.是");
                        System.out.println("2.否");
                        int choice2 = in.nextInt();
                        switch (choice2) {
                            case 1:
                                System.out.println("说几句简短的话鼓励商家吧~");
                                String comment = in.next();
                                UserBuyController.giveGoodComment(check.getSeller(),comment);
                                System.out.println("感谢您的好评");
                                break;
                            case 2:
                                System.out.println("感谢您的使用");
                                break;
                            default:
                        }
                        break;
                    case 2:
                        System.out.println("鉴定为假品，进行举报");
                        System.out.println("请描述情况");
                        String description = in.next();
                        UserBuyController.report(check.getSeller(),description);
                        System.out.println("感谢您的举报");
                        System.out.println("管理员会尽快处理");
                        break;
                    default:
                }

                break;
            case 2:
                System.out.println("请输入商品hash");
                String hash1 = in.next();
                UserItemStatusVO userItemStatusVO = UserBuyController.checkStatus(hash1);
                System.out.println("产品状态如下");
                System.out.println("时间:"+userItemStatusVO.getDate());
                System.out.println("状态:"+userItemStatusVO.getStatus());
                System.out.println("所在地:"+userItemStatusVO.getPlace());
                break;
            default:
        }
    }
    public static void showSupplierItem (List<Item> items, List<Item> itemList) throws SQLException, IOException {
        System.out.println("对外公布商品列表");
        showSimilar(itemList);
        System.out.println("真实信息列表");
        showSimilar(items);
        System.out.println("1.更新未售出产品的信息");
        System.out.println("2.更新已售出产品的状态");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice) {
            case 1:
            System.out.println("请输入商品序号");
            int index = in.nextInt();
            System.out.println("请输入新名称");
            String name = in.next();
            in.nextLine();
            System.out.println("请输入新描述");
            String description = in.nextLine();
            System.out.println("请输入新价格");
            String price = in.nextLine();
            UserEntryController.updateItem(index,items,name, description, price);
            System.out.println("更新成功");
            break;
            case 2:
                System.out.println("请输入商品序号");
                int id = in.nextInt();
                System.out.println("请输入目前产品所在地");
                String location = in.next();
                System.out.println("请输入物流状态（未发货=0 运送中=1 已送达=2）");
                int logistics = in.nextInt();
                UserBuyController.updateLogistics(id,location,logistics);
                System.out.println("更新成功");
                break;

            default:
        }
    }

    private static void showSimilar(List<Item> itemList) {
        for (Item item : itemList) {
            if(item.getSold()){
                System.out.println("序号 "+item.getIndex()+"商品名称：" + item.getName() + " 商品价格：" + item.getPrice() + " 商品描述：" + item.getDescription()+" 已售出 ");
            }else {
                System.out.println("序号 "+item.getIndex()+"商品名称：" + item.getName() + " 商品价格：" + item.getPrice() + " 商品描述：" + item.getDescription()+" 未售出");
            }
        }
    }

    public static void showResult(UserTransactionVO userTransactionVO) {
        System.out.println("交易结果");
        Date date = new Date();
        System.out.println("交易时间："+date);
        System.out.println("交易买家："+ userTransactionVO.getBuyer());
        System.out.println("交易卖家："+ userTransactionVO.getSeller());
        System.out.println("交易商品："+ userTransactionVO.getName());
        System.out.println("交易验伪hash："+ userTransactionVO.getHash()+" 请妥善保管");
        System.out.println("交易后余额："+ userTransactionVO.getBalance());
    }
}
