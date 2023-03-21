package com.liujiahui.www.controller;

import com.liujiahui.www.entity.dto.UserItemStatusDTO;
import com.liujiahui.www.entity.dto.UserRealItemDTO;
import com.liujiahui.www.entity.dto.UserTransactionDTO;
import com.liujiahui.www.entity.po.Item;
import com.liujiahui.www.entity.vo.UserItemStatusVO;
import com.liujiahui.www.entity.vo.UserTranscationVO;
import com.liujiahui.www.service.UserItemService;
import com.liujiahui.www.solidity.ItemTrade;
import com.liujiahui.www.view.UserItemRegisterAndShowInterface;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户购买控制器
 *
 * @author 刘家辉
 * @date 2023/03/19
 */
public class UserBuyController {
    public static void buy(int id, List<Item> items) throws ContractException, SQLException, IOException {
        for (Item item : items) {
            if (item.getId() == id) {
                UserTransactionDTO userTransactionDTO = UserItemService.buyItem(item.getOwner(), item.getIndex());
                if (userTransactionDTO != null && userTransactionDTO.getReturnMessage() == null) {
                    String balance = userTransactionDTO.getBalance();
                    ItemTrade.ItemSoldEventResponse itemSoldEventResponse = userTransactionDTO.getItemSoldEventResponse();
                    String hash = Numeric.toHexString(itemSoldEventResponse.hash);
                    UserTranscationVO userTranscationVO = new UserTranscationVO();
                    userTranscationVO.setName(item.getName());
                    userTranscationVO.setHash(hash);
                    userTranscationVO.setBalance(balance);
                    userTranscationVO.setBuyer(itemSoldEventResponse.buyer);
                    userTranscationVO.setSeller(itemSoldEventResponse.seller);
                    UserItemRegisterAndShowInterface.showResult(userTranscationVO);
                }
            }
        }
    }

    public static void updateLogistics(int id, String logistics, int status) {
        UserItemService.updateLogistics(id, logistics, status);
    }

    public static UserTranscationVO check(String hash) throws ContractException, NoSuchAlgorithmException {
        UserRealItemDTO userRealItemDTO = UserItemService.checkByHash(hash);
        UserTranscationVO userTranscationVO = new UserTranscationVO();
        userTranscationVO.setName(userRealItemDTO.getName());
        userTranscationVO.setHash(hash);
        userTranscationVO.setDescription(userRealItemDTO.getDescription());
        return userTranscationVO;
    }

    public static UserItemStatusVO checkStatus(String hash1) throws ContractException {
        UserItemStatusDTO userItemStatusDTO = UserItemService.checkStatus(hash1);
        UserItemStatusVO userItemStatusVO = new UserItemStatusVO();
        userItemStatusVO.setDate(userItemStatusDTO.getDate());
        userItemStatusVO.setPlace(userItemStatusDTO.getPlace());
        userItemStatusVO.setStatus(userItemStatusDTO.getStatus());
        return userItemStatusVO;
    }
}
