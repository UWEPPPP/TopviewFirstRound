package com.liujiahui.www.service;

import com.liujiahui.www.entity.po.TraceFeedbackPO;

import java.util.List;

/**
 * 跟踪管理服务
 *
 * @author 刘家辉
 * @date 2023/03/31
 */
public interface TraceAdminService {
    /**
     * 得到所有反馈和投诉
     *
     * @return {@link List}<{@link TraceFeedbackPO}>
     * @throws Exception 异常
     */
    List<TraceFeedbackPO> getAllFeedbackAndComplaint() throws Exception;

    void checkAppeal(String hash1);
}
