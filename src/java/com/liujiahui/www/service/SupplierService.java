package com.liujiahui.www.service;

import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.bo.TraceItemBO;
import com.liujiahui.www.entity.bo.TraceItemUpdateBO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SupplierService {

    /**
     * 添加物品
     *
     * @param traceItemBO 跟踪项目博
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    void addItem(TraceItemBO traceItemBO) throws SQLException, IOException;

    /**
     * 更新项目
     *
     * @param traceItemUpdateBO 跟踪项目更新博
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    void updateItem(TraceItemUpdateBO traceItemUpdateBO) throws SQLException, IOException;

    /**
     * 更新物流
     *
     * @param index     指数
     * @param logistics 物流
     * @param status    状态
     */
    void updateLogistics(int index, String logistics, int status);

    /**
     * 显示项
     *
     * @return {@link Map}<{@link String}, {@link List}<{@link TraceItemPO}>>
     * @throws ContractException 合同例外
     * @throws SQLException      sqlexception异常
     * @throws IOException       ioexception
     */
    Map<String, List<TraceItemPO>> showItem() throws ContractException, SQLException, IOException;

    /**
     * 删除项目
     *
     * @param index  指数
     * @param choice 选择
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    void removeItem(int index, Boolean choice) throws SQLException, IOException;

    /**
     * 显示反馈
     *
     * @return {@link List}<{@link TraceFeedbackPO}>
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    List<TraceFeedbackPO> showFeedback() throws SQLException, IOException;

    /**
     * 展示牌
     *
     * @return {@link Integer}
     * @throws ContractException 合同例外
     */
    Integer showToken() throws ContractException;

    void appealFeedback(TraceFeedbackBO traceFeedbackBO) throws SQLException, IOException;
}
