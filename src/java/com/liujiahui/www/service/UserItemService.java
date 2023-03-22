package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserBuyDAO;
import com.liujiahui.www.dao.UserItemDAO;
import com.liujiahui.www.entity.bo.UserAddItemBO;
import com.liujiahui.www.entity.bo.UserItemUpdateBO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.dto.UserItemStatusDTO;
import com.liujiahui.www.entity.dto.UserRealItemDTO;
import com.liujiahui.www.entity.dto.UserTransactionDTO;
import com.liujiahui.www.entity.po.Item;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 添加物品服务
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserItemService {
    public static void addItem(UserAddItemBO userAddItemBO) throws SQLException, IOException {
        UserInformationSaveDTO instance = UserInformationSaveDTO.getInstance();
        ContractTradeService contractTradeServiceSolidity = instance.getItemTradeSolidity();
        TransactionReceipt transactionReceipt = contractTradeServiceSolidity.addItem(userAddItemBO.getRealName(), userAddItemBO.getPrice(), userAddItemBO.getRealDescription());
        Tuple1<BigInteger> addItemOutput = contractTradeServiceSolidity.getAddItemOutput(transactionReceipt);
        System.out.println(addItemOutput);
        System.out.println(addItemOutput.getValue1());
        UserItemDAO.addItem(userAddItemBO.getName(), userAddItemBO.getPrice(), userAddItemBO.getDescription(),instance.getContractAccount(),addItemOutput.getValue1(),instance.getUserName());
        System.out.println(addItemOutput.getValue1());
    }

    public static List<Item> showAllItem() throws SQLException, IOException {
        return UserItemDAO.showAllItem();
    }

    public static List<Item> showMyItem() throws SQLException, IOException {
        return UserItemDAO.showMyItem(UserInformationSaveDTO.getInstance().getContractAccount());
    }

    public static List<Item> showRealItem() throws ContractException {
        return UserItemDAO.showRealItem();
    }

    public static List<Item> showOutsideItem() throws SQLException, IOException {
        return UserItemDAO.showOutsideItem(UserInformationSaveDTO.getInstance().getContractAccount());
    }
    public static UserTransactionDTO buyItem(String seller, BigDecimal index) throws ContractException, SQLException, IOException {
        UserInformationSaveDTO instance = UserInformationSaveDTO.getInstance();
        ContractTradeService contractTradeServiceSolidity = instance.getItemTradeSolidity();
        BigInteger bigInteger = index.toBigInteger();
        bigInteger=bigInteger.subtract(BigInteger.ONE);
        TransactionReceipt transactionReceipt = contractTradeServiceSolidity.buyItem(seller, bigInteger);

        TransactionResponse transactionResponse = instance.getDecoder().decodeReceiptStatus(transactionReceipt);
        UserTransactionDTO userTransactionDTO = new UserTransactionDTO();
        if(!Objects.equals(transactionResponse.getReturnMessage(), "Success")){
            userTransactionDTO.setReturnMessage(transactionResponse.getReturnMessage());
            return null;
        }
        List<ContractTradeService.ItemSoldEventResponse> itemSoldEvents = contractTradeServiceSolidity.getItemSoldEvents(transactionReceipt);
        ContractTradeService.ItemSoldEventResponse itemSoldEventResponse = itemSoldEvents.get(0);
        System.out.println(itemSoldEventResponse);
        BigInteger bigInteger1 = index.toBigInteger();
        UserBuyDAO.buyItem(seller,bigInteger1,itemSoldEventResponse.hash);
        BigInteger balance = contractTradeServiceSolidity.getBalance();
        instance.setBalance(String.valueOf(balance));
        userTransactionDTO.setItemSoldEventResponse(itemSoldEventResponse);
        userTransactionDTO.setBalance(String.valueOf(balance));
        return userTransactionDTO;
    }

    public static UserRealItemDTO checkByHash(String hash) throws ContractException {
        byte[] bytes = Numeric.hexStringToByteArray(hash);
        Tuple3<String, String, String> realItem = UserInformationSaveDTO.getInstance().getItemTradeSolidity().getRealItem(bytes);

        UserRealItemDTO userRealItemDTO = new UserRealItemDTO();
        userRealItemDTO.setName(realItem.getValue1());
        userRealItemDTO.setDescription(realItem.getValue2());
        userRealItemDTO.setSeller(realItem.getValue3().toString());
        return userRealItemDTO;
    }

    public static void updateItem(UserItemUpdateBO userItemUpdateBO) throws SQLException, IOException {
        String oldName = userItemUpdateBO.getOldName();
        String name = userItemUpdateBO.getName();
        String description = userItemUpdateBO.getDescription();
        String price = userItemUpdateBO.getPrice();
        UserItemDAO.updateItem(oldName,name,description,price);
        UserInformationSaveDTO.getInstance().getItemTradeSolidity().updateItem(new BigInteger(String.valueOf(userItemUpdateBO.getIndex())), BigInteger.valueOf(Integer.parseInt(price)));
    }

    public static void updateLogistics(int index, String logistics, int status) {
          UserInformationSaveDTO.getInstance().getItemTradeSolidity().updateStatus(BigInteger.valueOf(index),logistics,BigInteger.valueOf(status));
    }

    public static UserItemStatusDTO checkStatus(String hash1) throws ContractException {
        byte[] bytes = Numeric.hexStringToByteArray(hash1);
        Tuple3<BigInteger, String, BigInteger> status = UserInformationSaveDTO.getInstance().getItemTradeSolidity().checkStatus(bytes);
        UserItemStatusDTO userItemStatusDTO = new UserItemStatusDTO();
        long longValue = status.getValue1().longValue();
        Date date = new Date(longValue * 1000);
        userItemStatusDTO.setDate(date);
        userItemStatusDTO.setPlace(status.getValue2());
        BigInteger value3 = status.getValue3();
        int intValue = value3.intValue();
        if(intValue==0){
          userItemStatusDTO.setStatus("未出库，正在准备中");
        }else if(intValue==1){
            userItemStatusDTO.setStatus("已出库，正在运送中");
    }else {
            userItemStatusDTO.setStatus("已送达");
        }
        return userItemStatusDTO;
}}
