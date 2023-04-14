package com.liujiahui.www.view;

import com.liujiahui.www.controller.CommonUsedController;
import com.liujiahui.www.controller.ConsumeController;
import com.liujiahui.www.entity.dto.TraceItemStatusDTO;
import com.liujiahui.www.entity.po.FeedbackPO;
import com.liujiahui.www.entity.po.ItemPO;
import com.liujiahui.www.entity.vo.TraceItemStatusVO;
import com.liujiahui.www.entity.vo.TraceTransactionVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static com.liujiahui.www.view.TraceSameView.showItem;

/**
 * 跟踪消费观点
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceConsumeView {
    public static void showMyItem(List<ItemPO> traceItems) {
        System.out.println("这是您已购入的商品列表");
        for (ItemPO traceItem : traceItems) {
            System.out.println(traceItem.getId() + " " + "商品名称：" + traceItem.getName() + " 商品价格：" + traceItem.getPrice() + " 商品描述:" + traceItem.getDescription() + " 商品hash:" + traceItem.getHashes());
        }
        System.out.println("1.通过产品购买时给予的hash值验伪");
        System.out.println("2.查看产品运送状态");
        System.out.println("3.查看产品的生命周期");
        System.out.println("4.返回上一级");
        Scanner in = new Scanner(System.in);
        ConsumeController consumeController = new ConsumeController();
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                System.out.println("请输入商品hash");
                String hash = in.next();
                TraceTransactionVO check = consumeController.checkByHash(hash);
                System.out.println("根据hash 查到的真实信息是");
                System.out.println("真实名字" + check.getName());
                System.out.println("真实属性" + check.getDescription());
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
                                consumeController.feedback(1, check.getSeller(), comment, check.getHash(), check.getName());
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
                        String description = in.next();consumeController.feedback(2, check.getSeller(), description, check.getHash(), check.getName());
                        consumeController.returnItem(check.getHash());
                        System.out.println("感谢您的举报,管理员将尽快处理");
                        System.out.println("已进行自动退款");
                        break;
                    default:
                }
                break;
            case 2:
                System.out.println("请输入商品hash");
                String hash1 = in.next();
                TraceItemStatusVO traceItemStatusVO = consumeController.checkStatus(hash1);
                System.out.println("产品状态如下");
                System.out.println("时间:" + traceItemStatusVO.getDate());
                System.out.println("状态:" + traceItemStatusVO.getStatus());
                System.out.println("所在地:" + traceItemStatusVO.getPlace());
                break;
            case 3:
                System.out.println("请输入商品hash");
                String hash3 = in.next();
                List<TraceItemStatusDTO> life = consumeController.checkLife(hash3);
                System.out.println("产品生命周期如下");
                for (TraceItemStatusDTO vo : life) {
                    System.out.println("时间:" + vo.getDate());
                    System.out.println("状态:" + vo.getStatus());
                    System.out.println("所在地:" + vo.getPlace());
                    System.out.println("--------------------------------------------------");
                }
                break;
            default:
        }
    }

    public static void showResult(TraceTransactionVO traceTransactionVO) {
        System.out.println("交易结果");
        Date date = new Date();
        System.out.println("交易时间：" + date);
        System.out.println("交易买家：" + traceTransactionVO.getBuyer());
        System.out.println("交易卖家：" + traceTransactionVO.getSeller());
        System.out.println("交易商品：" + traceTransactionVO.getName());
        System.out.println("交易验伪hash：" + traceTransactionVO.getHash() + " 请妥善保管");
        System.out.println("交易后余额：" + traceTransactionVO.getBalance());
    }

    public static void showAndBuyItemByConsumer(List<ItemPO> traceItems) {
        showItem(traceItems);
        CommonUsedController commonUsedController = new CommonUsedController();
        System.out.println("1:购买产品");
        System.out.println("2:查看卖家的历史");
        System.out.println("3:按照条件筛选产品");
        System.out.println("4:返回列表");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                System.out.println("请输入商品id");
                int id = in.nextInt();
                ConsumeController consumeController = new ConsumeController();
                consumeController.buy(id, traceItems);
                break;
            case 2:
                System.out.println("请输入卖家的名字");
                String name = in.next();
                commonUsedController.showHistory(name);
                break;
            case 3:
                System.out.println("1:按照价格筛选");
                System.out.println("2:按照关键词筛选");
                System.out.println("3:按照商品种类筛选");
                System.out.println("4:按照商家筛选");
                int choice1 = in.nextInt();
                CommonUsedController traceQueryController = new CommonUsedController();
                List<ItemPO> traceItem = new ArrayList<>();
                traceItem = TraceSupplyView.getTraceItemSameWay(in, choice1, traceQueryController, traceItem);
                showItem(traceItem);
            default:

        }
    }

    public static void showAppealResult(List<FeedbackPO> list) {
        for (FeedbackPO feedbacks : list) {
            if (feedbacks.isAppealResult()) {
                System.out.println("被举报人：" + feedbacks.getSeller()  + " 举报结果：被鉴定为恶意举报 将进行一定资产的没收");
            } else {
                System.out.println("被举报人：" + feedbacks.getSeller()  + " 举报结果：被鉴定为真实举报 将对商家进行处罚");
            }
        }
    }
}
