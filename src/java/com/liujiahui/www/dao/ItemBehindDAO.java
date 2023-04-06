package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public interface ItemBehindDAO {
    /**
     * 把所有项目
     *
     * @param accountAddress 账户地址
     * @return {@link List}<{@link TraceItemPO}>
     * @throws SQLException sqlexception异常
     */
    List<TraceItemPO> getAllItem(String accountAddress) throws SQLException;

    /**
     * 删除或恢复项目
     *
     * @param index  指数
     * @param choice 选择
     * @throws SQLException sqlexception异常
     */
    void removeOrRestoredItem(int index, Boolean choice) throws SQLException;

    /**
     * 显示和更新反馈
     *
     * @param accountAddress 账户地址
     * @return {@link List}<{@link TraceFeedbackPO}>
     * @throws SQLException sqlexception异常
     */
    List<TraceFeedbackPO> showAndUpdateFeedback(String accountAddress) throws SQLException;

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
     * @return {@link TraceItemPO}
     * @throws SQLException sqlexception异常
     */
    TraceItemPO returnItem(String hash) throws SQLException;

    /**
     * 购买物品
     *
     * @param accountAddress 账户地址
     * @param index          指数
     * @throws SQLException sqlexception异常
     */
    void buyItem(String accountAddress, BigInteger index) throws SQLException;
}
