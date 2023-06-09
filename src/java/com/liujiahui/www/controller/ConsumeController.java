package com.liujiahui.www.controller;

import com.liujiahui.www.controller.proxy.ProxyFactory;
import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.dto.TraceItemStatusDTO;
import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.dto.TraceTransactionDTO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.entity.po.ItemPO;
import com.liujiahui.www.entity.vo.TraceItemStatusVO;
import com.liujiahui.www.entity.vo.TraceTransactionVO;
import com.liujiahui.www.service.ConsumerService;
import com.liujiahui.www.service.factory.TraceFactoryService;
import com.liujiahui.www.view.TraceConsumeView;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.utils.Numeric;

import java.util.List;

import static com.liujiahui.www.view.TraceConsumeView.showResult;

/**
 * 用户购买控制器
 *
 * @author 刘家辉
 * @date 2023/03/19
 */
public class ConsumeController {
    private static final ConsumerService CONSUMER_SERVICE = (ConsumerService) ProxyFactory.createProxy(TraceFactoryService.getConsumeUsedService());

    public void buy(int id, List<ItemPO> items) {
        for (ItemPO item : items) {
            if (item.getId() == id) {
                TraceTransactionDTO traceTransactionDTO = CONSUMER_SERVICE.buyItem(item.getOwner(), item.getIndex());
                if (traceTransactionDTO != null && traceTransactionDTO.getReturnMessage() == null) {
                    String balance = traceTransactionDTO.getBalance();
                    Tuple3<String, String, byte[]> itemSoldEventResponse = traceTransactionDTO.getItemSoldEventResponse();
                    String hash = Numeric.toHexString(itemSoldEventResponse.getValue3());
                    TraceTransactionVO traceTransactionVO = new TraceTransactionVO();
                    traceTransactionVO.setName(item.getName());
                    traceTransactionVO.setHash(hash);
                    traceTransactionVO.setBalance(balance);
                    traceTransactionVO.setBuyer(itemSoldEventResponse.getValue2());
                    traceTransactionVO.setSeller(itemSoldEventResponse.getValue1());
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
        List<ItemPO> items = CONSUMER_SERVICE.showItem().get("item");
        TraceConsumeView.showMyItem(items);
    }

    public void feedback(int score, String seller, String comment, String itemHash, String name) {
        TraceFeedbackBO traceFeedbackBO = new TraceFeedbackBO();
        traceFeedbackBO.setSeller(seller);
        traceFeedbackBO.setComment(comment);
        traceFeedbackBO.setItemHash(itemHash);
        traceFeedbackBO.setChoice(score);
        traceFeedbackBO.setBuyer(UserSaveDTO.getInstance().getContractAccount());
        traceFeedbackBO.setItemName(name);
        CONSUMER_SERVICE.supplierWriteDownService(traceFeedbackBO);
    }


    public void returnItem(String hash2) {
        CONSUMER_SERVICE.returnItem(hash2);
    }

    public List<TraceItemStatusDTO> checkLife(String hash3) {
        return CONSUMER_SERVICE.checkLife(hash3);
    }
}
