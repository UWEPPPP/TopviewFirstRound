package com.liujiahui.www.dao;

import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.util.UtilDAO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 跟踪用户刀
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public interface TraceUserDAO {

    /**
     * 更新个人信息
     *
     * @param type     类型
     * @param change   改变
     * @param identity 身份
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    static void updatePersonalInformation(String type, String change, String identity) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String name = TraceInformationSaveDTO.getInstance().getUserName();
        String sql = "update user." + identity + " set " + type + " = ? where user_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, change);
        preparedStatement.setString(2, name);
        preparedStatement.executeUpdate();
        UtilDAO.close(connection, null, preparedStatement);
    }

    /**
     * 显示所有商品
     *
     * @return {@link List}<{@link TraceItemPO}>
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    static List<TraceItemPO> showAllItem() throws SQLException, IOException {
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
        UtilDAO.close(connection, null, preparedStatement);
        return list;
    }

    /**
     * 查看商家历史评价
     *
     * @param name 名字
     * @return {@link List}<{@link TraceFeedbackPO}>
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    static List<TraceFeedbackPO> getHistory(String name) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.suppliers where user_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet set = preparedStatement.executeQuery();
        String accountAddress = null;
        if (set.next()) {
            accountAddress = set.getString("account_address");
        }
        String sql1 = "SELECT t1.*, t2.name FROM user.consumer_feedback t1 INNER JOIN user.item_show t2 ON t1.item_hash = t2.hash where seller_account=?;";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setString(1, accountAddress);
        ResultSet set1 = preparedStatement1.executeQuery();
        List<TraceFeedbackPO> list = new ArrayList<>();
        while (set1.next()) {
            TraceFeedbackPO traceFeedbackPo = new TraceFeedbackPO();
            traceFeedbackPo.setBuyer(set1.getString("buyer_account"));
            traceFeedbackPo.setSeller(set1.getString("seller_account"));
            traceFeedbackPo.setLikeOrReport("like".equals(set1.getString("like_report")));
            traceFeedbackPo.setItemHash(set1.getString("item_hash"));
            traceFeedbackPo.setComment(set1.getString("comment"));
            traceFeedbackPo.setItemName(set1.getString("Name"));
            list.add(traceFeedbackPo);
        }
        UtilDAO.close(connection, set, preparedStatement);
        UtilDAO.close(null, set, preparedStatement1);
        return list;
    }

    /**
     * @param accountAddress 账户地址
     * @return {@link List}<{@link TraceFeedbackPO}>
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    static List<TraceFeedbackPO> showReportAndAppealResult(String accountAddress) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String identity = Objects.equals(TraceInformationSaveDTO.getInstance().getIdentity(), "consumer") ? "buyer_account" : "seller_account";
        String judge = "buyer_account".equals(identity) ? "consumer_is_read" : "supplier_is_read";
        String sql = "SELECT * FROM user.consumer_feedback INNER JOIN user.supplier_appeal on consumer_feedback.item_hash = supplier_appeal.item_hash  WHERE " + identity + "=? and is_appeal IS NOT NULL ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, accountAddress);
        ResultSet set = preparedStatement.executeQuery();
        List<TraceFeedbackPO> list = new ArrayList<>();
        while (set.next()) {
            TraceFeedbackPO traceFeedbackPo = new TraceFeedbackPO();
            traceFeedbackPo.setBuyer(set.getString("buyer_account"));
            traceFeedbackPo.setItemHash(set.getString("item_hash"));
            traceFeedbackPo.setAppealResult(set.getBoolean("appeal_result"));
            traceFeedbackPo.setSeller(set.getString("seller_account"));
            list.add(traceFeedbackPo);
        }
        String sql1 = "update user.supplier_appeal INNER JOIN user.consumer_feedback ON consumer_feedback.item_hash=supplier_appeal.item_hash  set " + judge + " = true where " + identity + "=?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setString(1, accountAddress);
        preparedStatement1.executeUpdate();
        UtilDAO.close(connection, set, preparedStatement);
        return list;
    }

    /**
     * 显示商品
     *
     * @param accountAddress 账户地址
     * @return {@link Map}<{@link String}, {@link List}<{@link TraceItemPO}>>
     * @throws ContractException 合同例外
     * @throws SQLException      sqlexception异常
     * @throws IOException       ioexception
     */
    Map<String, List<TraceItemPO>> showItem(String accountAddress) throws ContractException, SQLException, IOException;
}
