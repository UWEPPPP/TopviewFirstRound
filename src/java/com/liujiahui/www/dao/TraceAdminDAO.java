package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 跟踪管理刀
 *
 * @author 刘家辉
 * @date 2023/03/31
 */
public interface TraceAdminDAO {
    /**
     * 得到所有反馈和投诉
     *
     * @return {@link List}<{@link TraceFeedbackPO}>
     */
    List<TraceFeedbackPO> getAllFeedbackAndComplaint() throws SQLException, IOException;

    /**
     * 得到单项
     *
     * @param hash 哈希
     * @return {@link TraceItemPO}
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    TraceItemPO getSingleItem(String hash) throws SQLException, IOException;

    /**
     * 解决上诉
     */
    void resolveBadLikeOrAppeal(String hash, Boolean result) throws SQLException, IOException;
}
