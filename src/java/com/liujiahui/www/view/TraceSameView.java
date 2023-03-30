package com.liujiahui.www.view;

import com.liujiahui.www.controller.TraceEntryController;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.entity.vo.TraceDetailedVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * 跟踪相同观点
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceSameView {
    private static final TraceEntryController USER_ENTRY_CONTROLLER = new TraceEntryController();
    static Scanner in = new Scanner(System.in);


    public static void showItem(List<TraceItemPO> traceItemPos) {
        System.out.println("商品列表");
        for (TraceItemPO traceItemPo : traceItemPos) {
            if (traceItemPo.getSold()) {
                System.out.println("商品种类" + traceItemPo.getType() + "序号" + traceItemPo.getId() + " " + "商品名称：" + traceItemPo.getName() + " 商品价格：" + traceItemPo.getPrice() + " 商品描述：" + traceItemPo.getDescription() + " 已售出 ");
            } else {
                System.out.println("商品种类" + traceItemPo.getType() + "序号" + traceItemPo.getId() + " " + "商品名称：" + traceItemPo.getName() + " 商品价格：" + traceItemPo.getPrice() + " 商品描述：" + traceItemPo.getDescription() + " 未售出 卖家：" + traceItemPo.getOwnerName());
            }
        }
    }

    public static void showPersonalInterface(TraceDetailedVO traceDetailedVO) throws SQLException, IOException {
        System.out.println("用户名：" + traceDetailedVO.getUserName());
        System.out.println("身份：" + traceDetailedVO.getIdentify());
        System.out.println("性别：" + traceDetailedVO.getGender());
        System.out.println("电话：" + traceDetailedVO.getPhone());
        System.out.println("余额：" + traceDetailedVO.getBalance());
        System.out.println("是否进行个人信息的修改？");
        System.out.println("1.修改用户名");
        System.out.println("2.修改性别");
        System.out.println("3.修改电话");
        System.out.println("4.否");
        int choice = in.nextInt();
        switch (choice) {
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
        if (choice != 4) {
            String change = in.next();
            TraceEntryController traceEntryController = new TraceEntryController();
            traceEntryController.changeUser(choice, change);
        }
        System.out.println("修改结束,是否继续修改？");
        System.out.println("1.是");
        System.out.println("2.否");
        int choice2 = in.nextInt();
        switch (choice2) {
            case 1:
                showPersonalInterface(traceDetailedVO);
                break;
            case 2:
                break;
            default:
        }
    }

    public static void showHistory(List<TraceFeedbackPO> history) {
        System.out.println("以下为该卖家的历史评价：");
        for (TraceFeedbackPO traceFeedbackPo : history) {
            System.out.println("买家：" + traceFeedbackPo.getBuyer());
            System.out.println("是否推荐：" + traceFeedbackPo.getLikeOrReport());
            System.out.println("评价：" + traceFeedbackPo.getComment());
            System.out.println("商品名字： " + traceFeedbackPo.getItemName());
            System.out.println("商品哈希：" + traceFeedbackPo.getItemHash());
            System.out.println("------------------------------------------------");
        }
    }
}
