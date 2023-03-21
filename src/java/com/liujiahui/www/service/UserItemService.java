package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserBuyDAO;
import com.liujiahui.www.dao.UserItemAddAndShowDAO;
import com.liujiahui.www.entity.bo.AddItemBO;
import com.liujiahui.www.entity.dto.RealItemDTO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.dto.UserTransactionDTO;
import com.liujiahui.www.entity.po.Item;
import com.liujiahui.www.solidity.ItemTrade;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

/**
 * 添加物品服务
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserItemService {
    public static void addItem(AddItemBO addItemBO) throws SQLException, IOException {
        UserInformationSaveDTO instance = UserInformationSaveDTO.getInstance();
        ItemTrade itemTradeSolidity = instance.getItemTradeSolidity();
        TransactionReceipt transactionReceipt = itemTradeSolidity.addItem(addItemBO.getRealName(), addItemBO.getPrice(), addItemBO.getRealDescription());
        Tuple1<BigInteger> addItemOutput = itemTradeSolidity.getAddItemOutput(transactionReceipt);
        System.out.println(addItemOutput);
        System.out.println(addItemOutput.getValue1());
        UserItemAddAndShowDAO.addItem(addItemBO.getName(),addItemBO.getPrice(),addItemBO.getDescription(),instance.getAccount(),addItemOutput.getValue1());
        System.out.println(addItemOutput.getValue1());
    }

    public static List<Item> showAllItem() throws SQLException, IOException {
        return UserItemAddAndShowDAO.showAllItem();
    }

    public static List<Item> showMyItem() throws SQLException, IOException {
        return UserItemAddAndShowDAO.showMyItem(UserInformationSaveDTO.getInstance().getAccount());
    }

    public static List<Item> showSoldItem() throws SQLException, IOException, ContractException {
        return UserItemAddAndShowDAO.showSoldItem();
    }
    public static UserTransactionDTO buyItem(String seller, BigDecimal index) throws ContractException, SQLException, IOException {
        UserInformationSaveDTO instance = UserInformationSaveDTO.getInstance();
        ItemTrade itemTradeSolidity = instance.getItemTradeSolidity();
        BigInteger bigInteger = index.toBigInteger();
        bigInteger=bigInteger.subtract(BigInteger.ONE);
        TransactionReceipt transactionReceipt = itemTradeSolidity.buyItem(seller, bigInteger);
        TransactionResponse transactionResponse = instance.getDecoder().decodeReceiptStatus(transactionReceipt);
        UserTransactionDTO userTransactionDTO = new UserTransactionDTO();
        if(transactionResponse.getReturnMessage()!=null){
            userTransactionDTO.setReturnMessage(transactionResponse.getReturnMessage());
            return null;
        }
        BigInteger bigInteger1 = index.toBigInteger();
        UserBuyDAO.buyItem(seller,bigInteger1);
        List<ItemTrade.ItemSoldEventResponse> itemSoldEvents = itemTradeSolidity.getItemSoldEvents(transactionReceipt);
        ItemTrade.ItemSoldEventResponse itemSoldEventResponse = itemSoldEvents.get(0);
        BigInteger balance = itemTradeSolidity.getBalance();
        instance.setBalance(String.valueOf(balance));
        userTransactionDTO.setItemSoldEventResponse(itemSoldEventResponse);
        userTransactionDTO.setBalance(String.valueOf(balance));
        return userTransactionDTO;
    }

    public static RealItemDTO checkByHash(String hash) throws ContractException {
        Tuple2<String, String> realItem = UserInformationSaveDTO.getInstance().getItemTradeSolidity().getRealItem(hash.getBytes());
        RealItemDTO realItemDTO = new RealItemDTO();
        realItemDTO.setName(realItem.getValue1());
        realItemDTO.setDescription(realItem.getValue2());
        return realItemDTO;
    }
}
