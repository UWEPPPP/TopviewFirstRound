package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.util.UtilDAO;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项显示刀
 *
 * @author 刘家辉
 * @date 2023/04/05
 */
public class ItemShowDAO {
    public List<TraceItemPO> showAllItem() throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.item_show INNER JOIN user.item_behind on item_show.hash = item_behind.hash where isRemoved = false";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet set = preparedStatement.executeQuery();
        List<TraceItemPO> list = new ArrayList<>();
        while (set.next()) {
            BigInteger price = new BigInteger(String.valueOf(set.getBigDecimal("price")));
            TraceItemPO traceItemPo = new TraceItemPO(set.getInt("id"), set.getString("name"), price, set.getString("description"), set.getString("owner_address"), set.getBigDecimal("index"), set.getBoolean("isSold"), set.getString("owner_name"));
            traceItemPo.setType(set.getString("type"));
            list.add(traceItemPo);
        }
        UtilDAO.close(connection, preparedStatement,set);
        return list;
    }

}
