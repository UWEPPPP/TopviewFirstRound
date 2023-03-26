package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.impl.TraceSupplierDAOImpl;
import com.liujiahui.www.dao.impl.TraceFactoryDAO;
import com.liujiahui.www.dao.TraceUserDAO;
import com.liujiahui.www.entity.bo.TraceAddItemBO;
import com.liujiahui.www.entity.bo.TraceItemUpdateBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import com.liujiahui.www.service.TraceItemPersonalService;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

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
    private TraceItemPersonalBySupplierServiceImpl() {
    }
    public static TraceItemPersonalBySupplierServiceImpl getInstance() {
        return INSTANCE;
    }

    private static final TraceUserDAO TRACE_USER_DAO = TraceFactoryDAO.getTraceFactoryDAO(true);
      public  void addItem(TraceAddItemBO traceAddItemBO) throws SQLException, IOException {
          TraceInformationSaveDTO instance = TraceInformationSaveDTO.getInstance();
          ContractTradeService contractTradeServiceSolidity = instance.getItemTradeSolidity();
          TransactionReceipt transactionReceipt = contractTradeServiceSolidity.addItem(traceAddItemBO.getRealName(), traceAddItemBO.getPrice(), traceAddItemBO.getRealDescription(), BigInteger.valueOf(traceAddItemBO.getType()));
          Tuple1<BigInteger> addItemOutput = contractTradeServiceSolidity.getAddItemOutput(transactionReceipt);
          ((TraceSupplierDAOImpl) TRACE_USER_DAO).addItem(traceAddItemBO.getName(), traceAddItemBO.getPrice(), traceAddItemBO.getDescription(),instance.getContractAccount(),addItemOutput.getValue1(),instance.getUserName(),traceAddItemBO.getType());
      }


      public void updateItem(TraceItemUpdateBO traceItemUpdateBO) throws SQLException, IOException {
       String oldName = traceItemUpdateBO.getOldName();
       String name = traceItemUpdateBO.getName();
       String description = traceItemUpdateBO.getDescription();
       String price = traceItemUpdateBO.getPrice();
       ((TraceSupplierDAOImpl) TRACE_USER_DAO).updateItem(oldName,name,description,price);
       TraceInformationSaveDTO.getInstance().getItemTradeSolidity().updateItem(new BigInteger(String.valueOf(traceItemUpdateBO.getIndex())), new BigInteger(price));
     }
     public  void updateLogistics(int index, String logistics, int status) {
         TraceInformationSaveDTO.getInstance().getItemTradeSolidity().updateStatus(BigInteger.valueOf(index),logistics,BigInteger.valueOf(status));
     }

    @Override
      public Map<String, List<TraceItemPO>> showItem() throws ContractException, SQLException, IOException {
        return  TRACE_USER_DAO.showItem(TraceInformationSaveDTO.getInstance().getContractAccount());
    }

}
