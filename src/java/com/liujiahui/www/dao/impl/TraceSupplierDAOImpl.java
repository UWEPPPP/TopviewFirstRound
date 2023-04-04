package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.TraceUserDAO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.wrapper.ContractStorageService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import com.liujiahui.www.util.UtilDAO;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    /**
     * 显示商家自己的产品
     *
     * @param accountAddress
     * @return {@link Map}<{@link String}, {@link List}<{@link TraceItemPO}>>
     * @throws ContractException 合同例外
     * @throws SQLException      sqlexception异常
     * @throws IOException       ioexception
     */
    @Override
    public Map<String, List<TraceItemPO>> showItem(String accountAddress) throws ContractException, SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "SELECT * FROM user.item_show INNER JOIN user.item_behind" +
                " ON item_show.hash = item_behind.hash WHERE item_behind.seller_address = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, accountAddress);
        ResultSet set = preparedStatement.executeQuery();
        List<TraceItemPO> list = new ArrayList<>();
        while (set.next()) {
            BigInteger price = new BigInteger(String.valueOf(set.getBigDecimal("price")));
            TraceItemPO traceItemPo = new TraceItemPO(set.getInt("id"), set.getString("name"), price, set.getString("description"), set.getString("owner_address"), set.getBigDecimal("index"), set.getBoolean("isSold"));
            traceItemPo.setType(set.getString("type"));
            list.add(traceItemPo);
        }
        TraceInformationSaveDTO instance = TraceInformationSaveDTO.getInstance();
        DynamicArray<ContractStorageService.Item> soldItem = instance.getItemTradeSolidity().getSoldItems();
        List<TraceItemPO> list1 = new ArrayList<>();
        int index = 0;
        for (ContractStorageService.Item item : soldItem.getValue()) {
            TraceItemPO traceItemPoOne = new TraceItemPO(item.name, item.price, item.description);
            traceItemPoOne.setIndex(new BigDecimal(index));
            traceItemPoOne.setSold(item.isSold);
            list1.add(traceItemPoOne);
            index++;
        }
        Map<String, List<TraceItemPO>> map = new HashMap<>(2);
        map.put("Outside", list);
        map.put("real", list1);
        UtilDAO.close(connection, null, preparedStatement);
        return map;
    }


    public void addItem(String name, BigInteger price, String description, String accountAddress, BigInteger index, String userName, int type, String hash, BigInteger token) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "insert into user.item_show (name, price, description,owner_name,type,hash) values (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        BigDecimal bigDecimal = new BigDecimal(price);
        preparedStatement.setBigDecimal(2, bigDecimal);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, userName);
        String itemType = null;
        switch (type) {
            case 0:
                itemType = "Food";
                break;
            case 1:
                itemType = "Clothes";
                break;
            case 2:
                itemType = "Electronic";
                break;
            case 3:
                itemType = "Furniture";
                break;
            case 4:
                itemType = "Other";
                break;
            default:
        }
        preparedStatement.setString(5, itemType);
        preparedStatement.setString(6, hash);
        preparedStatement.executeUpdate();
        String sql1 = "insert into user.item_behind (owner_address,isSold,`index`,seller_address,hash,isRemoved,token) values (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setString(1, accountAddress);
        preparedStatement1.setBoolean(2, false);
        preparedStatement1.setBigDecimal(3, new BigDecimal(index));
        preparedStatement1.setString(4, accountAddress);
        preparedStatement1.setString(5, hash);
        //是否下架了
        preparedStatement1.setBoolean(6, false);
        preparedStatement1.setBigDecimal(7, BigDecimal.valueOf(token.longValue()));
        preparedStatement1.executeUpdate();
    }


    public void updateItem(String oldName, String name, String description, String price) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "update user.item_show set name = ?,description = ?,price = ? where name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);
        BigDecimal bigDecimal = new BigDecimal(price);
        preparedStatement.setBigDecimal(3, bigDecimal);
        preparedStatement.setString(4, oldName);
        preparedStatement.executeUpdate();
        UtilDAO.close(connection, null, preparedStatement);
    }

    public void removeOrRestoredItem(int index, Boolean choice) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "update user.item_behind set isRemoved = ?  where `index`=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBoolean(1, choice);
        preparedStatement.setInt(2, index);
        preparedStatement.executeUpdate();
        UtilDAO.close(connection, null, preparedStatement);
        ContractTradeService itemTradeSolidity = TraceInformationSaveDTO.getInstance().getItemTradeSolidity();
        itemTradeSolidity.removeItem(BigInteger.valueOf(index), choice);
    }

    public List<TraceFeedbackPO> showFeedback(String accountAddress) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "SELECT * FROM user.consumer_feedback INNER JOIN user.item_show on item_hash = item_show.hash  WHERE seller_account = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, accountAddress);
        ResultSet set = preparedStatement.executeQuery();
        List<TraceFeedbackPO> list = new ArrayList<>();
        while (set.next()) {
            TraceFeedbackPO traceFeedbackPo = new TraceFeedbackPO();
            traceFeedbackPo.setBuyer(set.getString("buyer_account"));
            traceFeedbackPo.setSeller(set.getString("seller_account"));
            traceFeedbackPo.setLikeOrReport(Objects.equals(set.getString("like_report"), "likes"));
            traceFeedbackPo.setComment(set.getString("comment"));
            traceFeedbackPo.setItemName(set.getString("name"));
            traceFeedbackPo.setRead(set.getBoolean("is_read"));
            traceFeedbackPo.setItemHash(set.getString("item_hash"));
            list.add(traceFeedbackPo);
        }
        String sql1 = "update user.consumer_feedback set is_read = true where seller_account = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setString(1, accountAddress);
        preparedStatement1.executeUpdate();
        UtilDAO.close(connection, set, preparedStatement);
        UtilDAO.close(null, null, preparedStatement1);
        return list;
    }

    public void appealFeedback(String hash, String comment) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "insert into user.supplier_appeal  ( supplier_is_read, consumer_is_read, item_hash, supplier_comment) values(false,false,?,?)  ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, hash);
        preparedStatement.setString(2, comment);
        preparedStatement.executeUpdate();
        String sql1 = "update user.consumer_feedback set is_appeal = true where item_hash = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setString(1, hash);
        preparedStatement1.executeUpdate();
        UtilDAO.close(connection, null, preparedStatement);
        UtilDAO.close(null, null, preparedStatement1);
    }


}
