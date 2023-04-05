package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.TraceFeedbackPO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ConsumerFeedbackDAO {
    int getFeedbackNumber(String address);

    List<TraceFeedbackPO> getSupplierHistory(String accountAddress) throws SQLException, IOException;

    void updateFeedback(String hash) throws SQLException, IOException;

    void writeDown(String seller, String buyer, String comment, int choice, String itemHash) throws SQLException, IOException;

    List<TraceFeedbackPO> getAllFeedbackAndComplaint() throws SQLException, IOException;
}
