package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserTraceHistoryDAO;
import com.liujiahui.www.entity.bo.UserFeedbackBO;
import com.liujiahui.www.entity.po.Feedback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户反馈服务
 *
 * @author 刘家辉
 * @date 2023/03/22
 */
public class UserFeedbackService {
    public static void supplierWriteDownService(UserFeedbackBO userFeedbackBO) throws SQLException, IOException {
        String buyer = userFeedbackBO.getBuyer();
        String seller = userFeedbackBO.getSeller();
        String itemHash = userFeedbackBO.getItemHash();
        String comment = userFeedbackBO.getComment();
        int choice = userFeedbackBO.getChoice();
        UserTraceHistoryDAO.writeDown(seller,buyer,comment,choice,itemHash);
    }

    public static List<Feedback> getHistory(String name) throws SQLException, IOException {
        return UserTraceHistoryDAO.getHistory(name);
    }
}
