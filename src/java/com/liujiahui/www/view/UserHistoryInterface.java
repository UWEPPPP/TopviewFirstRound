package com.liujiahui.www.view;

import com.liujiahui.www.entity.po.Feedback;

import java.util.List;

/**
 * 用户历史界面
 *
 * @author 刘家辉
 * @date 2023/03/23
 */
public class UserHistoryInterface {
    public static void showHistory(List<Feedback> history) {
        System.out.println("以下为该卖家的历史评价：");
        for (Feedback feedback : history) {
            System.out.println("买家：" + feedback.getBuyer());
            System.out.println("是否推荐：" + feedback.getLikeOrReport());
            System.out.println("评价：" + feedback.getComment());
            System.out.println("商品名字： "+ feedback.getItemName());
            System.out.println("商品哈希：" + feedback.getItemHash());
            System.out.println("------------------------------------------------");
        }
    }
}
