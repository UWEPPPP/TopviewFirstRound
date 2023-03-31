package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.TraceUserDAO;
import com.liujiahui.www.dao.impl.TraceFactoryDAO;
import com.liujiahui.www.dao.impl.TraceSupplierDAOImpl;
import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.bo.TraceItemBO;
import com.liujiahui.www.entity.bo.TraceItemUpdateBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.TraceItemPersonalService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 添加物品服务
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class TraceItemPersonalBySupplierServiceImpl implements TraceItemPersonalService {
    private static final TraceItemPersonalBySupplierServiceImpl INSTANCE = new TraceItemPersonalBySupplierServiceImpl();
    private static final TraceUserDAO TRACE_USER_DAO = TraceFactoryDAO.getTraceFactoryDAO(true);

    private TraceItemPersonalBySupplierServiceImpl() {
    }

    public static TraceItemPersonalBySupplierServiceImpl getInstance() {
        return INSTANCE;
    }

    public void addItem(TraceItemBO traceItemBO) throws SQLException, IOException {
        TraceInformationSaveDTO instance = TraceInformationSaveDTO.getInstance();
        ContractTradeService contractTradeServiceSolidity = instance.getItemTradeSolidity();
        TransactionReceipt transactionReceipt = contractTradeServiceSolidity.addItem(traceItemBO.getRealName(), traceItemBO.getPrice(), traceItemBO.getRealDescription(), BigInteger.valueOf(traceItemBO.getType()), traceItemBO.getToken());
        Tuple2<BigInteger, byte[]> addItemOutput = contractTradeServiceSolidity.getAddItemOutput(transactionReceipt);
        contractTradeServiceSolidity.updateStatus(addItemOutput.getValue1(), traceItemBO.getLocation(), BigInteger.valueOf(3));
        contractTradeServiceSolidity.updateStatus(addItemOutput.getValue1(), traceItemBO.getStorage(), BigInteger.valueOf(4));
        String hash = Numeric.toHexString(addItemOutput.getValue2());
        ((TraceSupplierDAOImpl) TRACE_USER_DAO).addItem(traceItemBO.getName(), traceItemBO.getPrice(), traceItemBO.getDescription(), instance.getContractAccount(), addItemOutput.getValue1(), instance.getUserName(), traceItemBO.getType(), hash);
    }


    public void updateItem(TraceItemUpdateBO traceItemUpdateBO) throws SQLException, IOException {
        String oldName = traceItemUpdateBO.getOldName();
        String name = traceItemUpdateBO.getName();
        String description = traceItemUpdateBO.getDescription();
        String price = traceItemUpdateBO.getPrice();
        ((TraceSupplierDAOImpl) TRACE_USER_DAO).updateItem(oldName, name, description, price);
        TraceInformationSaveDTO.getInstance().getItemTradeSolidity().updateItem(new BigInteger(String.valueOf(traceItemUpdateBO.getIndex())), new BigInteger(price));
    }

    public void updateLogistics(int index, String logistics, int status) {
        TraceInformationSaveDTO.getInstance().getItemTradeSolidity().updateStatus(BigInteger.valueOf(index), logistics, BigInteger.valueOf(status));
    }

    @Override
    public Map<String, List<TraceItemPO>> showItem() throws ContractException, SQLException, IOException {
        return TRACE_USER_DAO.showItem(TraceInformationSaveDTO.getInstance().getContractAccount());
    }

    public void removeItem(int index, Boolean choice) throws SQLException, IOException {
        ((TraceSupplierDAOImpl) TRACE_USER_DAO).removeOrRestoredItem(index, choice);
    }

    public List<TraceFeedbackPO> showFeedback() throws SQLException, IOException {
        String contractAccount = TraceInformationSaveDTO.getInstance().getContractAccount();
        return ((TraceSupplierDAOImpl) TRACE_USER_DAO).showFeedback(contractAccount);
    }

    public Integer showToken() throws ContractException {
        TraceInformationSaveDTO instance = TraceInformationSaveDTO.getInstance();
        String contractAccount = instance.getContractAccount();
        return instance.getItemTradeSolidity().showSupplierToken(contractAccount).intValue();
    }

    public void appealFeedback(TraceFeedbackBO traceFeedbackBO) throws SQLException, IOException {
        ((TraceSupplierDAOImpl) TRACE_USER_DAO).appealFeedback(traceFeedbackBO.getItemHash(), traceFeedbackBO.getComment());
    }
}
