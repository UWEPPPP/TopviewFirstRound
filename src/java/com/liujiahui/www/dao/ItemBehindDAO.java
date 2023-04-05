package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public interface ItemBehindDAO {
    List<TraceItemPO> getAllItem(String accountAddress) throws SQLException, IOException;

    void removeOrRestoredItem(int index, Boolean choice) throws SQLException, IOException;

    List<TraceFeedbackPO> showAndUpdateFeedback(String accountAddress) throws SQLException, IOException;

    void insert(String name, String accountAddress, BigInteger index, String hash, BigInteger token) throws SQLException, IOException;

    TraceItemPO returnItem(String hash) throws SQLException, IOException;

    void buyItem(String accountAddress, BigInteger index) throws SQLException, IOException;
}
