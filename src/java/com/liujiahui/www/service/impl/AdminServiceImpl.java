package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.TraceAdminDAO;
import com.liujiahui.www.dao.impl.TraceFactoryDAO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.AdminService;
import com.liujiahui.www.service.wrapper.ContractStorageService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import com.liujiahui.www.util.CryptoUtil;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;

import java.io.IOException;
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
    private final TraceAdminDAO traceAdminDAO = TraceFactoryDAO.getTraceAdminDAO();
    private String buyer;
    private String seller;
    private BigInteger token;

    private AdminServiceImpl() {
    }

    public static AdminServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<TraceFeedbackPO> getAllFeedbackAndComplaint() throws Exception {
        return traceAdminDAO.getAllFeedbackAndComplaint();
    }

    @Override
    public TraceRealAndOutItemDTO checkItem(String hash1) throws ContractException, SQLException, IOException {
        ContractTradeService itemTradeSolidity = TraceInformationSaveDTO.getInstance().getItemTradeSolidity();
        ContractStorageService.Item singleItem = itemTradeSolidity.getSingleItem(Numeric.hexStringToByteArray(hash1));
        TraceItemPO singleItem1 = traceAdminDAO.getSingleItem(hash1);
        TraceRealAndOutItemDTO traceRealAndOutItemDTO = new TraceRealAndOutItemDTO();
        traceRealAndOutItemDTO.setRealName(singleItem.name);
        traceRealAndOutItemDTO.setRealDescription(singleItem.description);
        traceRealAndOutItemDTO.setOutName(singleItem1.getName());
        traceRealAndOutItemDTO.setOutDescription(singleItem1.getDescription());
        buyer = singleItem1.getOwner();
        seller = singleItem1.getSeller();
        token = BigInteger.valueOf(singleItem1.getToken());
        return traceRealAndOutItemDTO;
    }

    @Override
    public void resolveBadLikeOrAppeal(String hash1, Boolean result, Boolean choice) throws SQLException, IOException {
        ContractTradeService itemTradeSolidity = TraceInformationSaveDTO.getInstance().getItemTradeSolidity();
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
        traceAdminDAO.resolveBadLikeOrAppeal(hash1, result);
    }

    @Override
    public Boolean login(String password) {
        String content = CryptoUtil.readPassword("src/resource/password.txt");
        String adminKey = CryptoUtil.readPassword("src/resource/adminKey.txt");
        BcosSDK sdk = BcosSDK.build("config-example.toml");
        Client client = sdk.getClient(1);
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        CryptoKeyPair keyPair = cryptoSuite.createKeyPair(adminKey);
        ContractTradeService contractTradeService = ContractTradeService.load("0x2a8e6f2d815a4e44de6d5377763228256a3e64d9", client, keyPair);
        TraceInformationSaveDTO.getInstance().setItemTradeSolidity(contractTradeService);
        return Objects.equals(password, content);
    }
}
