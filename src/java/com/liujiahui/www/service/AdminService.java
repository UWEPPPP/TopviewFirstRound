package com.liujiahui.www.service;

import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;
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
     * @return {@link List}<{@link TraceFeedbackPO}>
     */
    List<TraceFeedbackPO> getAllFeedbackAndComplaint();

    /**
     * @param hash1 hash1
     * @return
     */
    TraceRealAndOutItemDTO checkItem(String hash1) throws ContractException;

    /**
     * 解决上诉
     *
     * @param hash1  hash1
     * @param result
     * @param choice
     */
    void resolveBadLikeOrAppeal(String hash1, Boolean result, Boolean choice);

    /**
     * @param password
     * @return {@link Boolean}
     */
    Boolean login(String password);
}
