package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.FeedbackPO;

import java.sql.SQLException;
import java.util.List;

/**
 * 供应商吸引力刀
 *
 * @author 刘家辉
 * @date 2023/04/06
 */
public interface SupplierAppealDAO {
    /**
     * 得到结果吸引力大小
     *
     * @param account       账户
     * @param identityCheck 身份检查
     * @param judge         法官
     * @return int
     */
    int getResultAppealSize(String account, String identityCheck, String judge);

    /**
     * 结果显示报告和吸引力
     *
     * @param accountAddress 账户地址
     * @return {@link List}<{@link FeedbackPO}>
     * @throws SQLException sqlexception异常
     */
    List<FeedbackPO> showReportAndAppealResult(String accountAddress) throws SQLException;

    /**
     * 更新结果读
     *
     * @param accountAddress 账户地址
     * @param identity       身份
     * @param judge          法官
     */
    void updateResultRead(String accountAddress, String identity, String judge);

    /**
     * 插入
     *
     * @param hash    哈希
     * @param comment 评论
     * @throws SQLException sqlexception异常
     */
    void insert(String hash, String comment) throws SQLException;

    /**
     * 解决恶意上诉
     *
     * @param hash   哈希
     * @param result 结果
     */
    void resolveBadAppeal(String hash, Boolean result);
}
