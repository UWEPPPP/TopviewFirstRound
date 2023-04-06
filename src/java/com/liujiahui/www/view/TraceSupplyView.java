package com.liujiahui.www.view;

import com.liujiahui.www.controller.TraceEntryController;
import com.liujiahui.www.controller.TraceQueryController;
import com.liujiahui.www.controller.TraceSupplyController;
import com.liujiahui.www.entity.po.FeedbackPO;
import com.liujiahui.www.entity.po.ItemPO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 跟踪供应视图
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceSupplyView {

    private static final TraceEntryController USER_ENTRY_CONTROLLER = new TraceEntryController();
    public static TraceSupplyController traceSupplyController = new TraceSupplyController();
    static Scanner in = new Scanner(System.in);

    public static void registerItem() {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入产品种类");
        System.out.println("食品：0  服装：1  电子产品：2   家具：3  其他：4 ");
        int type = in.nextInt();
        in.nextLine();
        System.out.println("请输入产品名称");
        String name = in.nextLine();
        System.out.println("请输入产品价格");
        BigInteger price = in.nextBigInteger();
        in.nextLine();
        //读取换行符
        System.out.println("请输入商品描述");
        String description = in.nextLine();
        BigInteger token;
        do {
            System.out.println("请输入投入的token");
            System.out.println("注：必须10以上及为10的倍数");
            token = in.nextBigInteger();
        } while (!(token.intValue() % 10 == 0 && token.intValue() >= 10));
        in.nextLine();
        System.out.println("--模拟商品真实属性--");
        System.out.println("如果商品为正品，请保证以上信息与商品真实信息一致");
        System.out.println("请输入商品真实名称");
        String realName = in.nextLine();
        System.out.println("请输入商品真实描述");
        String realDescription = in.nextLine();
        System.out.println("关于时间---------------------");
        System.out.println("在真正应用中会自动使用solidity的函数来获得当前时间");
        System.out.println("在这里也直接使用当前时间来当做生产时间，不过多模拟");
        System.out.println("----------------------------");
        System.out.println("模拟生产");
        System.out.println("请输入生产地点");
        String location = in.nextLine();
        System.out.println("请输入生产完后存储地点");
        String storage = in.nextLine();
        TraceSupplyController traceSupplyController = new TraceSupplyController();
        traceSupplyController.registerItem(name, price, description, realName, realDescription, type, location, storage, token);
        System.out.println("商品上架成功");
    }

    public static void showAndBuyItemBySupplier(List<ItemPO> pos) {
        showItem(pos);
        System.out.println("1:查看卖家的历史");
        System.out.println("2:按照条件筛选商品");
        System.out.println("3:返回列表");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                System.out.println("请输入卖家的名字");
                String name = in.next();
                USER_ENTRY_CONTROLLER.showHistory(name);
                break;
            case 2:
                System.out.println("1:按照价格筛选");
                System.out.println("2:按照关键词筛选");
                System.out.println("3:按照商品种类筛选");
                System.out.println("4:按照商家筛选");
                int choice1 = in.nextInt();
                TraceQueryController traceQueryController = new TraceQueryController();
                List<ItemPO> traceItem = new ArrayList<>();
                traceItem = getTraceItemSameWay(in, choice1, traceQueryController, traceItem);
                showItem(traceItem);
            default:

        }
    }

    static List<ItemPO> getTraceItemSameWay(Scanner in, int choice1, TraceQueryController traceQueryController, List<ItemPO> traceItem) {
        switch (choice1) {
            case 1:
                System.out.println("请输入你希望的最高价位");
                int price = in.nextInt();
                System.out.println("请输入你希望的最低价位");
                int price1 = in.nextInt();
                System.out.println("1:按照价格从低到高筛选");
                System.out.println("2:按照价格从高到低筛选");
                int choice2 = in.nextInt();

                switch (choice2) {
                    case 1:
                        traceItem = traceQueryController.queryByPrice(price1, price, 1);
                        break;
                    case 2:
                        traceItem = traceQueryController.queryByPrice(price1, price, 2);
                        break;
                    default:
                }
                break;
            case 2:
                System.out.println("请输入关键词");
                String keyword = in.next();
                traceItem = traceQueryController.queryByKeyword(keyword);
                break;
            case 3:
                System.out.println("请输入商品种类");
                String type = in.next();
                traceItem = traceQueryController.queryByType(type);
                break;
            case 4:
                System.out.println("请输入商家名字");
                String seller = in.next();
                traceItem = traceQueryController.queryBySeller(seller);
                break;
            default:
        }
        return traceItem;
    }

    public static void showSupplierItem(Map<String, List<ItemPO>> items) {
        System.out.println("对外公布商品列表");
        showItem(items.get("Outside"));
        System.out.println("真实信息列表");
        showRealItem(items.get("real"));
        System.out.println("1.更新未售出产品的信息");
        System.out.println("2.更新已售出产品的状态");
        System.out.println("3.下架or恢复商品");
        System.out.println("4.返回列表");

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
                traceSupplyController.updateItem(index, items.get("Outside"), name, description, price);
                System.out.println("更新成功");
                break;
            case 2:
                System.out.println("请输入商品序号");
                int id = in.nextInt();
                System.out.println("请输入目前产品所在地");
                String location = in.next();
                System.out.println("请输入物流状态（未发货=0 运送中=1 已送达=2）");
                int logistics = in.nextInt();
                traceSupplyController.updateLogistics(id, location, logistics);
                System.out.println("更新成功");
                break;
            case 3:
                System.out.println("请输入商品下标");
                int index1 = in.nextInt();
                System.out.println("下架还是恢复上架(输入true or false)");
                boolean status = in.nextBoolean();
                traceSupplyController.removeItem(index1, status);
                System.out.println("操作成功");
                break;
            default:
        }
    }

    public static void showItem(List<ItemPO> pos) {
        for (ItemPO itemPo : pos) {
            if (itemPo.getSold()) {
                System.out.println(itemPo.getId() + " " + "商品名称：" + itemPo.getName() + " 商品价格：" + itemPo.getPrice() + " 商品描述：" + itemPo.getDescription() + " 已售出 ");
            } else {
                System.out.println(itemPo.getId() + " " + "商品名称：" + itemPo.getName() + " 商品价格：" + itemPo.getPrice() + " 商品描述：" + itemPo.getDescription() + "index" + itemPo.getIndex() + " 未售出 ");
            }
        }
    }

    public static void showRealItem(List<ItemPO> pos) {
        for (ItemPO po : pos) {
            System.out.println("商品真实名称：" + po.getName() + " 商品真实描述：" + po.getDescription());
        }
    }

    public static void showAllFeedback(List<FeedbackPO> pos) {
        for (FeedbackPO po : pos) {
            if (po.getRead()) {
                System.out.println("已读的反馈 " + "反馈者：" + po.getBuyer() + " 类型：" + (po.getLikeOrReport() ? "好评" : "差评") + " 反馈内容：" + po.getComment() + " 反馈物品：" + po.getItemName() + " 物品hash:" + po.getItemHash());
            } else {
                System.out.println("未读的新反馈！" + "反馈者：" + po.getBuyer() + " 类型：" + (po.getLikeOrReport() ? "好评" : "差评") + " 反馈内容：" + po.getComment() + " 反馈物品：" + po.getItemName() + " 物品hash:" + po.getItemHash());
            }
        }
        System.out.println("1.对不实的反馈进行申诉");
        System.out.println("2.退出");
        int choice = in.nextInt();
        if (choice == 1) {
            System.out.println("请输入对应物品hash");
            String hash = in.next();
            System.out.println("请输入申诉内容");
            String comment = in.next();
            traceSupplyController.appealFeedback(hash, comment);
            System.out.println("已经申诉，等待管理员处理");
        }
    }

    public static void showToken(Integer integer) {
        System.out.println("您的token为：" + integer);
    }

    public static void showAppealResult(List<FeedbackPO> traceFeedbacks) {
        for (FeedbackPO po : traceFeedbacks) {
            if (po.isAppealResult()) {
                System.out.println("申诉内容  " + "举报者：" + po.getBuyer() + "举报物品：" + po.getItemHash() + "申诉结果：" + "申诉成功，举报已驳回");
            } else {
                System.out.println("申诉内容  " + "举报者：" + po.getBuyer() + "举报物品：" + po.getItemHash() + "申诉结果：" + "申诉失败，举报已通过");
            }
        }
    }
}
