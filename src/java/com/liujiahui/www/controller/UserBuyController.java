package com.liujiahui.www.controller;

import com.liujiahui.www.entity.dto.UserTransactionDTO;
import com.liujiahui.www.entity.po.Item;
import com.liujiahui.www.entity.vo.TranscationVO;
import com.liujiahui.www.service.UserItemService;
import com.liujiahui.www.solidity.ItemTradeSolidity;
import com.liujiahui.www.view.UserItemRegisterAndShowInterface;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
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
                String balance = userTransactionDTO.getBalance();
                ItemTradeSolidity.ItemSoldEventResponse itemSoldEventResponse = userTransactionDTO.getItemSoldEventResponse();
                String buyer = itemSoldEventResponse.buyer;
                String seller = itemSoldEventResponse.seller;
                TranscationVO transcationVO = new TranscationVO();
                transcationVO.setName(item.getName());
                transcationVO.setPrice(itemSoldEventResponse.price);
                transcationVO.setBuyer(buyer);
                transcationVO.setSeller(seller);
                transcationVO.setBalance(balance);
                UserItemRegisterAndShowInterface.showResult(transcationVO);
            }
        }
    }
}
