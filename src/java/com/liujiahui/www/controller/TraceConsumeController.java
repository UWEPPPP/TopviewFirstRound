package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.dto.TraceItemStatusDTO;
import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.dto.TraceTransactionDTO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.entity.vo.TraceItemStatusVO;
import com.liujiahui.www.entity.vo.TraceTransactionVO;
import com.liujiahui.www.service.ConsumerService;
import com.liujiahui.www.service.impl.TraceFactoryService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import com.liujiahui.www.view.TraceConsumeView;
import org.fisco.bcos.sdk.utils.Numeric;

import java.util.List;

import static com.liujiahui.www.view.TraceConsumeView.showResult;

/**
 * 用户购买控制器
 *
 * @author 刘家辉
 * @date 2023/03/19
 */
public class TraceConsumeController {
    private static final ConsumerService CONSUMER_SERVICE = TraceFactoryService.getConsumeUsedService();

    public void buy(int id, List<TraceItemPO> items) {
        for (TraceItemPO item : items) {
            if (item.getId() == id) {
                TraceTransactionDTO traceTransactionDTO = CONSUMER_SERVICE.buyItem(item.getOwner(), item.getIndex());
                if (traceTransactionDTO != null && traceTransactionDTO.getReturnMessage() == null) {
                    String balance = traceTransactionDTO.getBalance();
                    ContractTradeService.ItemSoldEventResponse itemSoldEventResponse = traceTransactionDTO.getItemSoldEventResponse();
                    String hash = Numeric.toHexString(itemSoldEventResponse.hash);
                    TraceTransactionVO traceTransactionVO = new TraceTransactionVO();
                    traceTransactionVO.setName(item.getName());
                    traceTransactionVO.setHash(hash);
                    traceTransactionVO.setBalance(balance);
                    traceTransactionVO.setBuyer(itemSoldEventResponse.buyer);
                    traceTransactionVO.setSeller(itemSoldEventResponse.seller);
                    showResult(traceTransactionVO);
                }
            }
        }
    }

    public TraceTransactionVO checkByHash(String hash) {
        TraceRealAndOutItemDTO traceRealAndOutItemDTO = CONSUMER_SERVICE.checkByHash(hash);
        TraceTransactionVO traceTransactionVO = new TraceTransactionVO();
        traceTransactionVO.setName(traceRealAndOutItemDTO.getRealName());
        traceTransactionVO.setHash(hash);
        traceTransactionVO.setDescription(traceRealAndOutItemDTO.getRealDescription());
        traceTransactionVO.setSeller(traceRealAndOutItemDTO.getSeller());
        return traceTransactionVO;
    }

    public TraceItemStatusVO checkStatus(String hash1) {
        TraceItemStatusDTO traceItemStatusDTO = CONSUMER_SERVICE.checkStatus(hash1);
        TraceItemStatusVO traceItemStatusVO = new TraceItemStatusVO();
        traceItemStatusVO.setDate(traceItemStatusDTO.getDate());
        traceItemStatusVO.setPlace(traceItemStatusDTO.getPlace());
        traceItemStatusVO.setStatus(traceItemStatusDTO.getStatus());
        return traceItemStatusVO;
    }

    public void showUserItem() {
        List<TraceItemPO> items = CONSUMER_SERVICE.showItem().get("item");
        TraceConsumeView.showMyItem(items);
    }

    public void feedback(int score, String seller, String comment, String itemHash) {
        TraceFeedbackBO traceFeedbackBO = new TraceFeedbackBO();
        traceFeedbackBO.setSeller(seller);
        traceFeedbackBO.setComment(comment);
        traceFeedbackBO.setItemHash(itemHash);
        traceFeedbackBO.setChoice(score);
        traceFeedbackBO.setBuyer(TraceInformationSaveDTO.getInstance().getContractAccount());
        CONSUMER_SERVICE.supplierWriteDownService(traceFeedbackBO);
    }


    public void returnItem(String hash2) {
        CONSUMER_SERVICE.returnItem(hash2);
    }

    public List<TraceItemStatusDTO> checkLife(String hash3) {
        return CONSUMER_SERVICE.checkLife(hash3);
    }
}
