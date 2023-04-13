package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.FeedbackPO;
import com.liujiahui.www.entity.po.ItemPO;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 刘家辉
 * @date 2023/04/14
 */
public interface ItemBehindDAO {
    /**
     * 把所有项目
     *
     * @param accountAddress 账户地址
     * @return {@link List}<{@link ItemPO}>
     * @throws SQLException sqlexception异常
     */
    List<ItemPO> getAllItem(String accountAddress) throws SQLException;

    /**
     * 删除或恢复项目
     *
     * @param index           指数
     * @param choice          选择
     * @param contractAccount
     * @throws SQLException sqlexception异常
     */
    void removeOrRestoredItem(int index, Boolean choice, String contractAccount) throws SQLException;

    /**
     * 显示和更新反馈
     *
     * @param accountAddress 账户地址
     * @return {@link List}<{@link FeedbackPO}>
     * @throws SQLException sqlexception异常
     */
    List<FeedbackPO> showAndUpdateFeedback(String accountAddress) throws SQLException;

    /**
     * 插入
     *
     * @param name           名字
     * @param accountAddress 账户地址
     * @param index          指数
     * @param hash           哈希
     * @param token          令牌
     * @throws SQLException sqlexception异常
     */
    void insert(String name, String accountAddress, BigInteger index, String hash, BigInteger token) throws SQLException;

    /**
     * 返回项目
     *
     * @param hash 哈希
     * @return {@link ItemPO}
     * @throws SQLException sqlexception异常
     */
    ItemPO returnItem(String hash) throws SQLException;

    /**
     * 购买物品
     *
     * @param accountAddress 账户地址
     * @param index          指数
     * @throws SQLException sqlexception异常
     */
    void buyItem(String accountAddress, BigInteger index) throws SQLException;
}
