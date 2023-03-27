package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.TraceQueryDAO;
import com.liujiahui.www.dao.util.UtilDAO;
import com.liujiahui.www.entity.po.TraceItemPO;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 条件查询实现类
 * @author 刘家辉
 * @date 2023/03/26
 */
public class TraceQueryDAOImpl implements TraceQueryDAO {
    private static final TraceQueryDAOImpl TRACE_QUERY_DAO = new TraceQueryDAOImpl();
    private TraceQueryDAOImpl() {}
    public static TraceQueryDAOImpl getInstance() {
        return TRACE_QUERY_DAO;
    }
    @Override
    public List<TraceItemPO> queryByPrice(int min,int max,int choice) {
        String order;
        if(choice==1) {
            order = "asc";
        }else {
            order = "desc";
        }
        try(Connection connection=UtilDAO.getConnection()) {
            String sql="select * from user.item_show where price between ? and ? order by price  "+order;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,min);
            preparedStatement.setInt(2,max);
            preparedStatement.setString(3,order);
            return querySame(preparedStatement);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TraceItemPO> queryByKeyword(String keyword) {
        try(Connection connection=UtilDAO.getConnection()) {
            String sql="select * from user.item_show where name like ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+keyword+"%");
            return querySame(preparedStatement);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TraceItemPO> queryByType(String type) {
        try(Connection connection=UtilDAO.getConnection()) {
            String sql="select * from user.item_show where type=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,type);
            return querySame(preparedStatement);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TraceItemPO> queryBySeller(String seller) {
        try(Connection connection=UtilDAO.getConnection()) {
            String sql="select * from user.item_behind INNER JOIN item_show ON item_behind.hash " +
                    "= item_show.hash where seller_address=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,seller);
            return querySame(preparedStatement);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    private List<TraceItemPO> querySame(PreparedStatement preparedStatement) throws SQLException {
        ResultSet set = preparedStatement.executeQuery();
        List<TraceItemPO> list= new ArrayList<>();
        while (set.next()) {
            TraceItemPO po=new TraceItemPO();
            po.setId(set.getInt("id"));
            po.setName(set.getString("name"));
            po.setPrice(BigInteger.valueOf(set.getInt("price")));
            po.setDescription(set.getString("description"));
            po.setOwner(set.getString("owner"));
            po.setOwnerName(set.getString("owner_name"));
            po.setIndex(set.getBigDecimal("index"));
            po.setSold(set.getBoolean("isSold"));
            po.setType(set.getString("type"));
            list.add(po);
        }
        return list;
    }
}
