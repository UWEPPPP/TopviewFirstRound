package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.factory.TraceFactoryDAO;
import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.entity.po.FeedbackPO;
import com.liujiahui.www.entity.po.ItemPO;
import com.liujiahui.www.service.AdminService;
import com.liujiahui.www.service.wrapper.ContractProxyService;
import com.liujiahui.www.service.wrapper.ContractStorageService;
import com.liujiahui.www.util.CryptoUtil;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.utils.Numeric;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * 跟踪管理服务impl
 *
 * @author 刘家辉
 * @date 2023/03/31
 */
public class AdminServiceImpl implements AdminService {
    private static final AdminServiceImpl INSTANCE = new AdminServiceImpl();
    private String buyer;
    private String seller;
    private BigInteger token;

    private AdminServiceImpl() {
    }

    public static AdminServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<FeedbackPO> getAllFeedbackAndComplaint() {
        try {
            return TraceFactoryDAO.getConsumerFeedbackDAO().getAllFeedbackAndComplaint();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public TraceRealAndOutItemDTO checkItem(String hash1) {
        ContractProxyService itemTradeSolidity = UserSaveDTO.getInstance().getItemTradeSolidity();
        ContractStorageService.Item singleItem;
        TraceRealAndOutItemDTO traceRealAndOutItemDTO = new TraceRealAndOutItemDTO();
        TransactionReceipt receipt = itemTradeSolidity.getSingleItem(Numeric.hexStringToByteArray(hash1));
        Tuple1<ContractStorageService.Item> getSingleItemOutput = itemTradeSolidity.getGetSingleItemOutput(receipt);
        singleItem = getSingleItemOutput.getValue1();
        traceRealAndOutItemDTO.setRealName(singleItem.name);
        traceRealAndOutItemDTO.setRealDescription(singleItem.description);
        ItemPO singleItem1;
        try {
            singleItem1 = TraceFactoryDAO.getItemShowDAO().getSingleItem(hash1);
            traceRealAndOutItemDTO.setOutName(singleItem1.getName());
            traceRealAndOutItemDTO.setOutDescription(singleItem1.getDescription());
            token = BigInteger.valueOf(singleItem1.getToken());
            buyer = singleItem1.getOwner();
            seller = singleItem1.getSeller();
        } catch (SQLException e) {
            throw new RuntimeException("数据库异常");
        }
        return traceRealAndOutItemDTO;
    }

    @Override
    public void resolveBadLikeOrAppeal(String hash1, Boolean result, Boolean choice) {
        ContractProxyService itemTradeSolidity = UserSaveDTO.getInstance().getItemTradeSolidity();
        if (!choice) {
            //申诉判定
            if (result) {
                //鉴定为恶意举报
                itemTradeSolidity.resolveAppeal(buyer, seller, token, true);
                // 商家获补偿，用户扣除余额
            }
            //商家申诉失败 维护原样
        } else {
            //恶意点赞判定
            if (result) {
                //判定为恶意点赞
                itemTradeSolidity.resolveAppeal(buyer, seller, token, false);
                //商家扣除获得的token，用户扣除余额
            }
        }
        TraceFactoryDAO.getSupplierAppealDAO().resolveBadLikeOrAppeal(hash1, result);
    }

    @Override
    public Boolean login(String password) {
        String content = CryptoUtil.readPassword("src/resource/password.txt");
        String adminKey = CryptoUtil.readPassword("src/resource/adminKey.txt");
        BcosSDK sdk = BcosSDK.build("config-example.toml");
        Client client = sdk.getClient(1);
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        CryptoKeyPair keyPair = cryptoSuite.createKeyPair(adminKey);
        ContractProxyService contractTradeService = ContractProxyService.load("0xc26871383a3bf14fe2965e3b917c12c433ea3545", client, keyPair);
        UserSaveDTO.getInstance().setItemTradeSolidity(contractTradeService);
        return Objects.equals(password, content);
    }
}
