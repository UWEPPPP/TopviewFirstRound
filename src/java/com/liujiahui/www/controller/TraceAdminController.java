package com.liujiahui.www.controller;

import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.TraceAdminService;
import com.liujiahui.www.service.impl.TraceFactoryImplService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    public static void checkAppeal(String hash1) {
        traceAdminService.checkAppeal(hash1);
    }
}

