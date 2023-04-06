package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.FeedbackPO;

import java.sql.SQLException;
import java.util.List;

public interface ConsumerFeedbackDAO {
    /**
     * @param address 地址
     * @return int
     */
    int getFeedbackNumber(String address) throws SQLException;

    /**
     * @param accountAddress 账户地址
     * @return {@link List}<{@link FeedbackPO}>
     * @throws SQLException sqlexception异常
     */
    List<FeedbackPO> getSupplierHistory(String accountAddress) throws SQLException;

    /**
     * 更新反馈
     *
     * @param hash 哈希
     * @throws SQLException sqlexception异常
     */
    void updateFeedback(String hash) throws SQLException;

    /**
     * 写下来
     *
     * @param seller   卖方
     * @param buyer    买家
     * @param comment  评论
     * @param type     类型
     * @param itemHash 项哈希
     * @throws SQLException sqlexception异常
     */
    void writeDown(String seller, String buyer, String comment, String type, String itemHash) throws SQLException;

    /**
     * 得到所有反馈和投诉
     *
     * @return {@link List}<{@link FeedbackPO}>
     * @throws SQLException sqlexception异常
     */
    List<FeedbackPO> getAllFeedbackAndComplaint() throws SQLException;
}
