package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.TraceUserDAO;
import com.liujiahui.www.dao.util.UtilDAO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.utils.Numeric;

import java.io.IOException;
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
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceConsumerDAOImpl implements TraceUserDAO {
    private static  final TraceConsumerDAOImpl TRACE_CONSUMER = new TraceConsumerDAOImpl();
    private TraceConsumerDAOImpl(){}
    public static TraceConsumerDAOImpl getInstance(){
        return TRACE_CONSUMER;
    }

    @Override
    public Map<String, List<TraceItemPO>> showItem(String accountAddress) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.item_show INNER JOIN user.item_behind on item_show.hash = item_behind.hash " +
                " where owner_address = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,accountAddress);
        ResultSet set = preparedStatement.executeQuery();
        List<TraceItemPO> list = new ArrayList<>();
        while (set.next()){
            BigInteger price = new BigInteger(String.valueOf(set.getBigDecimal("price")));
            TraceItemPO traceItem = new TraceItemPO(set.getInt("id"),set.getString("name"),price,set.getString("description"),set.getString("owner"),set.getBigDecimal("index"),set.getBoolean("isSold"), Numeric.hexStringToByteArray(set.getString("hash")));
            list.add(traceItem);
        }
        UtilDAO.close(connection,null,preparedStatement);
        HashMap<String,List<TraceItemPO>> listHashMap=new HashMap<>(1);
        listHashMap.put("item",list);
        return listHashMap;
    }

    public void returnItem(String hash) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "update user.item_behind set isSold = ?,owner_address=seller_address where hash = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        String sql1 = "select `index` from user.item_behind where hash = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement.setBoolean(1,false);
        preparedStatement.setString(2,hash);
        preparedStatement.executeUpdate();
        preparedStatement1.setString(1,hash);
        ResultSet set = preparedStatement1.executeQuery();
        if(set.next()){
            ContractTradeService itemTradeSolidity = TraceInformationSaveDTO.getInstance().getItemTradeSolidity();
            itemTradeSolidity.refundItem(Numeric.hexStringToByteArray(hash),set.getBigDecimal("index").toBigInteger());
        }
        UtilDAO.close(connection,null,preparedStatement);
    }

    public  void buyItem(String accountAddress, BigInteger id, byte[] hash) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String account = TraceInformationSaveDTO.getInstance().getContractAccount();
        String sql = "update user.item_behind set isSold = ?,owner_address = ?,hash=? where seller_address = ? and `index` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBoolean(1,true);
        preparedStatement.setString(2,account);
        String hexString = Numeric.toHexString(hash);
        preparedStatement.setString(3,hexString);
        preparedStatement.setString(4,accountAddress);
        preparedStatement.setBigDecimal(5,new java.math.BigDecimal(id));
        preparedStatement.executeUpdate();
        UtilDAO.close(connection,null,preparedStatement);
    }

    public  void writeDown(String seller, String buyer, String comment, int choice, String itemHash) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String type;
        if(choice == 1){
            type ="likes";
        }else {
            type ="reports";
        }
        String sql = "update user.suppliers set "+type+" = "+type+" + 1 where account_address = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,seller);
        preparedStatement.executeUpdate();
        String sql1 = "insert into user.consumer_feedback (seller_account,buyer_account,comment,like_report,item) values (?,?,?,?,?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setString(1,seller);
        preparedStatement1.setString(2,buyer);
        preparedStatement1.setString(3,comment);
        preparedStatement1.setString(4,type);
        preparedStatement1.setString(5,itemHash);
        preparedStatement1.executeUpdate();
        UtilDAO.close(connection,null,preparedStatement);
        UtilDAO.close(null,null,preparedStatement1);
    }


}
