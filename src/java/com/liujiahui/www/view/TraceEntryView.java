package com.liujiahui.www.view;

import com.liujiahui.www.controller.TraceEntryController;
import com.liujiahui.www.entity.vo.TraceAfterLoginVO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 消费者主界面
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class TraceEntryView {
    static Scanner in = new Scanner(System.in);
    public static void viewConsumer(TraceAfterLoginVO traceAfterLoginVO) throws ContractException, SQLException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if(traceAfterLoginVO !=null){
            System.out.println("您好消费者" + traceAfterLoginVO.getName() + "欢迎您来到Topview的产品溯源系统");
            System.out.println("您的余额为" + traceAfterLoginVO.getBalance());
            do {
                System.out.println("请选择您要进行的操作");
                System.out.println("1.查看产品信息");
                System.out.println("2.查看个人信息");
                System.out.println("3.查看我购买的产品");
            } while (mainInterface(false));
            TraceInitView.start();
    }else {
            System.out.println("用户名或者密码错误");
            System.out.println("即将返回登录界面");
            TraceInitView.start();
        }
    }

    private static boolean mainInterface(Boolean bool) throws SQLException, IOException, ContractException {
        System.out.println("0.退出登录");
        int choice = in.nextInt();
        if(choice!=0){
            TraceEntryController traceEntryController = new TraceEntryController();
            traceEntryController.entry(choice);
        }else {
            System.out.println("退出登录成功");
            System.out.println("即将返回登录界面");
            return false;
        }
        return true;
    }

    public static void viewSupplier(TraceAfterLoginVO traceAfterLoginVO) throws ContractException, SQLException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if(traceAfterLoginVO !=null){
            System.out.println("您好 供应商" + traceAfterLoginVO.getName() + "欢迎您来到Topview的产品溯源系统");
            System.out.println("您的余额为" + traceAfterLoginVO.getBalance());
            if (traceAfterLoginVO.getInformationSize() > 0) {
                System.out.println("您收到了" + traceAfterLoginVO.getInformationSize() + "则来自顾客的反馈，请前往查看");
            } else {
                System.out.println("没有最新评价");
            }
            do {
                System.out.println("请选择您要进行的操作");
                System.out.println("1.查看产品信息");
                System.out.println("2.查看个人信息");
                System.out.println("3.产品上新");
                System.out.println("4.进入我的产品主页");
                System.out.println("5.用户对我的评价");
                System.out.println("6.查看我收到的通知");
            } while (mainInterface(true));
            TraceInitView.start();
        }else {
            System.out.println("用户名或者密码错误");
            System.out.println("即将返回登录界面");
            TraceInitView.start();
        }
    }
}
