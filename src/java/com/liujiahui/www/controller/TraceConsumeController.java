package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.dto.TraceItemStatusDTO;
import com.liujiahui.www.entity.dto.TraceRealItemDTO;
import com.liujiahui.www.entity.dto.TraceTransactionDTO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.entity.vo.TraceItemStatusVO;
import com.liujiahui.www.entity.vo.TraceTransactionVO;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import com.liujiahui.www.service.impl.TraceFactoryImplService;
import com.liujiahui.www.service.TraceItemPersonalService;
import com.liujiahui.www.service.impl.TraceItemPersonalByConsumerServiceImpl;
import com.liujiahui.www.view.TraceConsumeView;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.liujiahui.www.view.TraceConsumeView.showResult;

/**
 * 用户购买控制器
 *
 * @author 刘家辉
 * @date 2023/03/19
 */
public class TraceConsumeController {
    private final TraceItemPersonalService traceItemPersonalService = TraceFactoryImplService.getTraceItemPersonalService(false);

    public  void buy(int id, List<TraceItemPO> items) throws ContractException, SQLException, IOException {
        for (TraceItemPO item : items) {
            if (item.getId() == id) {
                TraceTransactionDTO traceTransactionDTO = ((TraceItemPersonalByConsumerServiceImpl)traceItemPersonalService).buyItem(item.getOwner(), item.getIndex());
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
    public TraceTransactionVO checkByHash(String hash) throws ContractException {
        TraceRealItemDTO traceRealItemDTO = ((TraceItemPersonalByConsumerServiceImpl)traceItemPersonalService).checkByHash(hash);
        TraceTransactionVO traceTransactionVO = new TraceTransactionVO();
        traceTransactionVO.setName(traceRealItemDTO.getName());
        traceTransactionVO.setHash(hash);
        traceTransactionVO.setDescription(traceRealItemDTO.getDescription());
        traceTransactionVO.setSeller(traceRealItemDTO.getSeller());
        return traceTransactionVO;
    }

    public TraceItemStatusVO checkStatus(String hash1) throws ContractException {
        TraceItemStatusDTO traceItemStatusDTO = ((TraceItemPersonalByConsumerServiceImpl)traceItemPersonalService).checkStatus(hash1);
        TraceItemStatusVO traceItemStatusVO = new TraceItemStatusVO();
        traceItemStatusVO.setDate(traceItemStatusDTO.getDate());
        traceItemStatusVO.setPlace(traceItemStatusDTO.getPlace());
        traceItemStatusVO.setStatus(traceItemStatusDTO.getStatus());
        return traceItemStatusVO;
    }

    public void showUserItem() throws SQLException, IOException, ContractException {
        List<TraceItemPO> items = traceItemPersonalService.showItem().get("item");
        TraceConsumeView.showMyItem(items);
    }

    public  void feedback(int score,String seller, String comment, String itemHash) throws SQLException, IOException {
        TraceFeedbackBO traceFeedbackBO = new TraceFeedbackBO();
        traceFeedbackBO.setSeller(seller);
        traceFeedbackBO.setComment(comment);
        traceFeedbackBO.setItemHash(itemHash);
        traceFeedbackBO.setChoice(score);
        traceFeedbackBO.setBuyer(TraceInformationSaveDTO.getInstance().getContractAccount());
        ((TraceItemPersonalByConsumerServiceImpl)traceItemPersonalService).supplierWriteDownService(traceFeedbackBO);
    }


}
