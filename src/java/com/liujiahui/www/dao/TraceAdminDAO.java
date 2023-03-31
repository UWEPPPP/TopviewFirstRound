package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.TraceFeedbackPO;

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
}
