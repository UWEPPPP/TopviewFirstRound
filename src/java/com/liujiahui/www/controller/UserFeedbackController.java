package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.UserFeedbackBO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.po.Feedback;
import com.liujiahui.www.service.UserFeedbackService;
import com.liujiahui.www.view.UserHistoryInterface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户反馈控制器
 *
 * @author 刘家辉
 * @date 2023/03/22
 */
public class UserFeedbackController {
    public static void feedback(int score,String seller, String comment, String itemHash) throws SQLException, IOException {
        UserFeedbackBO userFeedbackBO = new UserFeedbackBO();
        userFeedbackBO.setSeller(seller);
        userFeedbackBO.setComment(comment);
        userFeedbackBO.setItemHash(itemHash);
        userFeedbackBO.setChoice(score);
        userFeedbackBO.setBuyer(UserInformationSaveDTO.getInstance().getContractAccount());
        UserFeedbackService.supplierWriteDownService(userFeedbackBO);
    }
    public static void showHistory(String name) throws SQLException, IOException {
        List<Feedback> history = UserFeedbackService.getHistory(name);
        UserHistoryInterface.showHistory(history);
    }
}
