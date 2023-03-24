package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.UserItemDAO;
import com.liujiahui.www.entity.bo.UserAddItemBO;
import com.liujiahui.www.entity.bo.UserItemUpdateBO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.po.Item;
import com.liujiahui.www.service.ContractTradeService;
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
    private static final TraceItemPersonalBySupplierServiceImpl SERVICE = new TraceItemPersonalBySupplierServiceImpl();
    private TraceItemPersonalBySupplierServiceImpl() {
    }
    public static TraceItemPersonalBySupplierServiceImpl getInstance() {
        return SERVICE;
    }
      public  void addItem(UserAddItemBO userAddItemBO) throws SQLException, IOException {
          UserInformationSaveDTO instance = UserInformationSaveDTO.getInstance();
          ContractTradeService contractTradeServiceSolidity = instance.getItemTradeSolidity();
          TransactionReceipt transactionReceipt = contractTradeServiceSolidity.addItem(userAddItemBO.getRealName(), userAddItemBO.getPrice(), userAddItemBO.getRealDescription());
          Tuple1<BigInteger> addItemOutput = contractTradeServiceSolidity.getAddItemOutput(transactionReceipt);
          System.out.println(addItemOutput);
          System.out.println(addItemOutput.getValue1());
          UserItemDAO.addItem(userAddItemBO.getName(), userAddItemBO.getPrice(), userAddItemBO.getDescription(),instance.getContractAccount(),addItemOutput.getValue1(),instance.getUserName());
          System.out.println(addItemOutput.getValue1());
      }


      public void updateItem(UserItemUpdateBO userItemUpdateBO) throws SQLException, IOException {
       String oldName = userItemUpdateBO.getOldName();
       String name = userItemUpdateBO.getName();
       String description = userItemUpdateBO.getDescription();
       String price = userItemUpdateBO.getPrice();
       UserItemDAO.updateItem(oldName,name,description,price);
       UserInformationSaveDTO.getInstance().getItemTradeSolidity().updateItem(new BigInteger(String.valueOf(userItemUpdateBO.getIndex())), BigInteger.valueOf(Integer.parseInt(price)));
     }
     public  void updateLogistics(int index, String logistics, int status) {
         UserInformationSaveDTO.getInstance().getItemTradeSolidity().updateStatus(BigInteger.valueOf(index),logistics,BigInteger.valueOf(status));
     }

    @Override
      public Map<String, List<Item>> showItem() throws ContractException, SQLException, IOException {
        return  UserItemDAO.showSupplierItem(UserInformationSaveDTO.getInstance().getContractAccount());
    }

}
