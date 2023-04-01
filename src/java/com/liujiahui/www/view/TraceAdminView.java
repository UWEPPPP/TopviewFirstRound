package com.liujiahui.www.view;

import com.liujiahui.www.controller.TraceAdminController;
import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;

import java.util.List;
import java.util.Scanner;

/**
 * 跟踪管理视图
 *
 * @author 刘家辉
 * @date 2023/03/31
 */
public class TraceAdminView {
    static Scanner input = new Scanner(System.in);

    public static void start() throws Exception {
        System.out.println("你好管理员");
        System.out.println("以下为所有用户的反馈信息");
        List<TraceFeedbackPO> feedbacks = TraceAdminController.showAllFeedbackAndComplaint();
        for (TraceFeedbackPO feedback : feedbacks) {
            if (feedback.getAppeal()) {
                System.out.println("------------------------------------");
                System.out.println("该用户提交的反馈被商家进行了申诉");
                System.out.println(feedback.getBuyer() + "用户的反馈信息为：");
                System.out.println(feedback);
                System.out.println("------------------------------------");
            }
        }
        System.out.println("对于用户点赞的信息你需要辨别是否恶意点赞");
        System.out.println("对于商家的申诉你需要进行处理");
        System.out.println("---------------------------------");
        do {
            System.out.println("1.辨别是否恶意点赞");
            System.out.println("2.处理商家申诉");
            System.out.println("3.退出");
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("请输入对应产品hash：");
                    String hash = input.next();
                    TraceAdminController.checkWilfulLikes(hash);
                    break;
                case 2:
                    System.out.println("请输入对应产品hash：");
                    String hash1 = input.next();
                    TraceRealAndOutItemDTO traceRealAndOutItemDTO = TraceAdminController.checkAppeal(hash1);
                    System.out.println("该产品的真实信息为：");
                    System.out.println(traceRealAndOutItemDTO.getRealName()+" 属性"+traceRealAndOutItemDTO.getRealDescription());
                    System.out.println("该产品的公布信息为：");
                    System.out.println(traceRealAndOutItemDTO.getOutName()+" 属性"+traceRealAndOutItemDTO.getOutDescription());
                    System.out.println("你的判断结果是？");
                    System.out.println("1.商家的申诉是正确的");
                    System.out.println("2.商家的申诉是错误的");
                    int choice1 = input.nextInt();
                    if (choice1 == 1) {
                        TraceAdminController.resolveAppeal(hash1);
                    } else if (choice1 == 2) {
                        System.out.println("还得写一个申诉驳回的提示功能");
                    } else {
                        System.out.println("输入错误，请重新输入！");
                    }
                    break;
                case 3:
                    System.out.println("感谢您的使用，再见！");
                    System.exit(0);
                    break;
                default:
                    System.out.println("输入错误，请重新输入！");
                    break;
            }
        } while (true);
    }
}
