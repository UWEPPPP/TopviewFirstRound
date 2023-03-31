package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.TraceAdminDAO;
import com.liujiahui.www.dao.impl.TraceFactoryDAO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.service.TraceAdminService;
import com.liujiahui.www.service.wrapper.ContractTradeService;

import java.util.List;

/**
 * 跟踪管理服务impl
 *
 * @author 刘家辉
 * @date 2023/03/31
 */
public class TraceAdminServiceImpl implements TraceAdminService {
    private static final TraceAdminServiceImpl INSTANCE = new TraceAdminServiceImpl();
    private final TraceAdminDAO traceAdminDAO = TraceFactoryDAO.getTraceAdminDAO();

    private TraceAdminServiceImpl() {
    }

    public static TraceAdminServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<TraceFeedbackPO> getAllFeedbackAndComplaint() throws Exception {
        return traceAdminDAO.getAllFeedbackAndComplaint();
    }

    @Override
    public void checkAppeal(String hash1) {
        ContractTradeService itemTradeSolidity = TraceInformationSaveDTO.getInstance().getItemTradeSolidity();
        //  itemTradeSolidity.get
    }
}
