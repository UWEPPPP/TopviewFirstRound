package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.TraceFeedbackPO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface SupplierAppealDAO {
    int getResultAppealSize(String account, String identityCheck, String judge);

    List<TraceFeedbackPO> showReportAndAppealResult(String accountAddress) throws SQLException, IOException;

    void updateResultRead(String accountAddress, String identity, String judge);

    void insert(String hash, String comment) throws SQLException, IOException;

    void resolveBadLikeOrAppeal(String hash, Boolean result) throws SQLException, IOException;
}
