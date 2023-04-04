package com.liujiahui.www.controller;

import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.service.AdminService;
import com.liujiahui.www.service.impl.TraceFactoryService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 跟踪管理控制器
 *
 * @author 刘家辉
 * @date 2023/03/31
 */
public class TraceAdminController {
    private static final AdminService ADMIN_SERVICE = TraceFactoryService.getTraceAdminService();

    public static Boolean adminLogin(String password) {
        return ADMIN_SERVICE.login(password);
    }

    public static List<TraceFeedbackPO> showAllFeedbackAndComplaint() throws Exception {
        return ADMIN_SERVICE.getAllFeedbackAndComplaint();
    }


    public static TraceRealAndOutItemDTO checkItem(String hash1) throws ContractException, SQLException, IOException {
        return ADMIN_SERVICE.checkItem(hash1);
    }

    public static void resolveBadLikeOrAppeal(String hash1, Boolean result, Boolean choice) throws SQLException, IOException {
        ADMIN_SERVICE.resolveBadLikeOrAppeal(hash1, result, choice);
    }
}

