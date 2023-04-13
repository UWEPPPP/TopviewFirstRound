package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.FeedbackPO;

import java.sql.SQLException;
import java.util.List;

/**
 * 消费者反馈刀
 *
 * @author 刘家辉
 * @date 2023/04/13
 */
public interface ConsumerFeedbackDAO {
    /**
     * 解决恶意点赞
     *
     * @param hash 哈希
     */
    void resolveBadLike(String hash);

    /**
     * 得到反馈数量
     *
     * @param address 地址
     * @return int
     * @throws SQLException sqlexception异常
     */
    int getFeedbackNumber(String address) throws SQLException;

    /**
     * 得到供应商历史
     *
     * @param accountAddress 账户地址
     * @return {@link List}<{@link FeedbackPO}>
     * @throws SQLException sqlexception异常
     */
    List<FeedbackPO> getSupplierHistory(String accountAddress) throws SQLException;

    /**
     * 更新反馈
     *
     * @param hash 哈希
     */
    void updateFeedback(String hash);

    /**
     * 写下来
     *
     * @param seller   卖方
     * @param buyer    买家
     * @param comment  评论
     * @param type     类型
     * @param itemHash 项哈希
     * @param itemName 物品名称
     * @throws SQLException sqlexception异常
     */
    void writeDown(String seller, String buyer, String comment, String type, String itemHash, String itemName) throws SQLException;

    /**
     * 得到所有反馈和投诉
     *
     * @return {@link List}<{@link FeedbackPO}>
     * @throws SQLException sqlexception异常
     */
    List<FeedbackPO> getAllFeedbackAndComplaint() throws SQLException;
}
