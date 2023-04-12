package com.liujiahui.www.controller;

import com.liujiahui.www.controller.proxy.ProxyFactory;
import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.po.FeedbackPO;
import com.liujiahui.www.service.AdminService;
import com.liujiahui.www.service.factory.TraceFactoryService;

import java.util.List;

/**
 * 跟踪管理控制器
 *
 * @author 刘家辉
 * @date 2023/03/31
 */
public class AdminController {
    private static final AdminService ADMIN_SERVICE = (AdminService) ProxyFactory.createProxy(TraceFactoryService.getTraceAdminService());

    public static Boolean adminLogin(String password) {
        return ADMIN_SERVICE.login(password);
    }

    public static List<FeedbackPO> showAllFeedbackAndComplaint() {
        return ADMIN_SERVICE.getAllFeedbackAndComplaint();
    }


    public static TraceRealAndOutItemDTO checkItem(String hash1) {
        return ADMIN_SERVICE.checkItem(hash1);
    }

    public static void resolveBadLikeOrAppeal(String hash1, Boolean result, Boolean choice) {
        ADMIN_SERVICE.resolveBadLikeOrAppeal(hash1, result, choice);
    }
}

