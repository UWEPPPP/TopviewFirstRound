package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.TraceItemPO;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ItemShowDAO {
    List<TraceItemPO> queryByPrice(int min, int max, int choice);

    List<TraceItemPO> queryByKeyword(String keyword);

    List<TraceItemPO> queryByType(String type);

    List<TraceItemPO> queryBySeller(String seller);

    List<TraceItemPO> showAllItem() throws SQLException;

    void updateItem(String oldName, String name, String description, String price) throws SQLException;

    void insert(String name, BigInteger price, String description, String userName, int type, String hash) throws SQLException;

    Map<String, List<TraceItemPO>> showConsumerItem(String accountAddress) throws SQLException;

    TraceItemPO getSingleItem(String hash) throws SQLException;
}
