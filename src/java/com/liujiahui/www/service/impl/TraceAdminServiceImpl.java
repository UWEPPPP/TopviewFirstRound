package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.TraceAdminDAO;
import com.liujiahui.www.dao.impl.TraceFactoryDAO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.TraceAdminService;
import com.liujiahui.www.service.wrapper.ContractStorageService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;

import java.io.IOException;
import java.sql.SQLException;
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
    public TraceRealAndOutItemDTO checkAppeal(String hash1) throws ContractException, SQLException, IOException {
        ContractTradeService itemTradeSolidity = TraceInformationSaveDTO.getInstance().getItemTradeSolidity();
        ContractStorageService.Item singleItem = itemTradeSolidity.getSingleItem(Numeric.hexStringToByteArray(hash1));
        TraceItemPO singleItem1 = traceAdminDAO.getSingleItem(hash1);
        TraceRealAndOutItemDTO traceRealAndOutItemDTO = new TraceRealAndOutItemDTO();
        traceRealAndOutItemDTO.setRealName(singleItem.name);
        traceRealAndOutItemDTO.setRealDescription(singleItem.description);
        traceRealAndOutItemDTO.setOutName(singleItem1.getName());
        traceRealAndOutItemDTO.setOutDescription(singleItem1.getDescription());
        return traceRealAndOutItemDTO;
    }

    @Override
    public void resolveAppeal(String hash1) {
        ContractTradeService itemTradeSolidity = TraceInformationSaveDTO.getInstance().getItemTradeSolidity();
        //调用合约！没写完！
    }
}
