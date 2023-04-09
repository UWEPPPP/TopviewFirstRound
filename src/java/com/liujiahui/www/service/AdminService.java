package com.liujiahui.www.service;

import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.po.FeedbackPO;

import java.util.List;

/**
 * 跟踪管理服务
 *
 * @author 刘家辉
 * @date 2023/03/31
 */
public interface AdminService {
    /**
     * 得到所有反馈和投诉
     *
     * @return {@link List}<{@link FeedbackPO}>
     */
    List<FeedbackPO> getAllFeedbackAndComplaint();

    /**
     * 检查项目
     *
     * @param hash1 hash1
     * @return {@link TraceRealAndOutItemDTO}
     */
    TraceRealAndOutItemDTO checkItem(String hash1);

    /**
     * 解决上诉
     *
     * @param hash1  hash1
     * @param result 结果
     * @param choice 选择
     */
    void resolveBadLikeOrAppeal(String hash1, Boolean result, Boolean choice);

    /**
     * 登录
     *
     * @param password 密码
     * @return {@link Boolean}
     */
    Boolean login(String password);
}
