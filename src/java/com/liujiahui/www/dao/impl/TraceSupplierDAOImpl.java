package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.TraceUserDAO;
import com.liujiahui.www.dao.util.UtilDAO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 跟踪供应商刀
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceSupplierDAOImpl implements TraceUserDAO {
    private static final TraceSupplierDAOImpl TRACE_SUPPLIER = new TraceSupplierDAOImpl();
    private TraceSupplierDAOImpl() {
    }
    public static TraceSupplierDAOImpl getInstance() {
        return TRACE_SUPPLIER;
    }
    @Override
    public Map<String, List<TraceItemPO>> showItem(String accountAddress) throws ContractException, SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.item where seller = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,accountAddress);
        ResultSet set = preparedStatement.executeQuery();
        List<TraceItemPO> list = new ArrayList<>();
        while (set.next()){
            BigInteger price = new BigInteger(String.valueOf(set.getBigDecimal("price")));
            TraceItemPO traceItemPo = new TraceItemPO(set.getInt("id"),set.getString("name"),price,set.getString("description"),set.getString("owner"),set.getBigDecimal("index"),set.getBoolean("isSold"));
            traceItemPo.setType(set.getString("type"));
            list.add(traceItemPo);
        }
        TraceInformationSaveDTO instance = TraceInformationSaveDTO.getInstance();
        DynamicArray<ContractTradeService.Item> soldItem = instance.getItemTradeSolidity().getSoldItem();
        List<TraceItemPO> list1=new ArrayList<>();
        int index=0;
        for (ContractTradeService.Item item : soldItem.getValue()) {
            TraceItemPO traceItemPoOne = new TraceItemPO(item.name,item.price,item.description);
            traceItemPoOne.setIndex(new BigDecimal(index));
            traceItemPoOne.setSold(item.isSold);
            list1.add(traceItemPoOne);
            index++;
        }
        Map<String,List<TraceItemPO>> map = new HashMap<>(2);
        map.put("Outside",list);
        map.put("real",list1);
        UtilDAO.close(connection,null,preparedStatement);
        return map;
    }
    public  void addItem(String name, BigInteger price, String description, String accountAddress, BigInteger index,String userName,int type) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "insert into user.item (name, price, description, owner, `index`,isSold,seller,owner_name,type) values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        BigDecimal bigDecimal = new BigDecimal(price);
        preparedStatement.setBigDecimal(2,bigDecimal);
        preparedStatement.setString(3,description);
        preparedStatement.setString(4,accountAddress);
        long longValue = index.longValue();
        preparedStatement.setLong(5,longValue);
        preparedStatement.setBoolean(6,false);
        preparedStatement.setString(7,accountAddress);
        preparedStatement.setString(8,userName);
        String itemType = null;
        switch (type){
            case 0:
                itemType="Food";
                break;
            case 1:
                itemType="Clothes";
                break;
            case 2:
                itemType="Electronic";
                break;
            case 3:
                itemType="Furniture";
                break;
            case 4:
                itemType="Other";
                break;
            default:
        }
        preparedStatement.setString(9,itemType);
        preparedStatement.executeUpdate();
    }


    public  void updateItem(String oldName, String name, String description, String price) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "update user.item set name = ?,description = ?,price = ? where name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,description);
        BigDecimal bigDecimal = new BigDecimal(price);
        preparedStatement.setBigDecimal(3,bigDecimal);
        preparedStatement.setString(4,oldName);
        preparedStatement.executeUpdate();
        UtilDAO.close(connection,null,preparedStatement);
    }


}
