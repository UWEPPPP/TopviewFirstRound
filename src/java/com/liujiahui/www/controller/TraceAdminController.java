package com.liujiahui.www.controller;

import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.service.TraceAdminService;
import com.liujiahui.www.service.impl.TraceFactoryImplService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;

/**
 * 跟踪管理控制器
 *
 * @author 刘家辉
 * @date 2023/03/31
 */
public class TraceAdminController {
    private static final TraceAdminService traceAdminService = TraceFactoryImplService.getTraceAdminService();

    public static Boolean adminLogin(String password) {
        try {
            File file = new File("src/resource/password.txt8");
            String content = new String(Files.readAllBytes(file.toPath()));
            return content.equals(password);
        } catch (IOException e) {
            System.out.println("读取文件失败");
            e.printStackTrace();
        }
        return false;
    }

    public static List<TraceFeedbackPO> showAllFeedbackAndComplaint() throws Exception {
        return traceAdminService.getAllFeedbackAndComplaint();
    }

    public static void checkWilfulLikes(String hash) {
    }

    public static TraceRealAndOutItemDTO checkAppeal(String hash1) throws ContractException, SQLException, IOException {
        return traceAdminService.checkAppeal(hash1);
    }

    public static void resolveAppeal(String hash1) {
        traceAdminService.resolveAppeal(hash1);
    }
}

