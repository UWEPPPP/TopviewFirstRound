package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.TraceAdminDAO;
import com.liujiahui.www.dao.impl.TraceFactoryDAO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.TraceAdminService;
import com.liujiahui.www.service.wrapper.ContractStorageService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;

/**
 * 跟踪管理服务impl
 *
 * @author 刘家辉
 * @date 2023/03/31
 */
public class TraceAdminServiceImpl implements TraceAdminService {
    private static final TraceAdminServiceImpl INSTANCE = new TraceAdminServiceImpl();
    private final TraceAdminDAO traceAdminDAO = TraceFactoryDAO.getTraceAdminDAO();
    private String buyer;
    private String seller;
    private BigInteger token;

    private TraceAdminServiceImpl() {
    }

    public static TraceAdminServiceImpl getInstance() {
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
        itemTradeSolidity.resolveAppeal(buyer, seller, token, choice);
        traceAdminDAO.resolveBadLikeOrAppeal(hash1, result);
    }

    @Override
    public Boolean login(String password) {
        try {
            File file = new File("src/resource/password.txt");
            String content = new String(Files.readAllBytes(file.toPath()));
            File admin = new File("src/resource/adminKey.txt");
            String adminKey = new String(Files.readAllBytes(admin.toPath()));
            BcosSDK sdk = BcosSDK.build("config-example.toml");
            Client client = sdk.getClient(1);
            CryptoSuite cryptoSuite = client.getCryptoSuite();
            CryptoKeyPair keyPair = cryptoSuite.createKeyPair(adminKey);
            ContractTradeService contractTradeService = ContractTradeService.load("0xf0e12e6437b3387a68f7789607998ea9c4f72d5b", client, keyPair);
            TraceInformationSaveDTO.getInstance().setItemTradeSolidity(contractTradeService);
            return content.equals(password);
        } catch (IOException e) {
            System.out.println("读取文件失败");
            e.printStackTrace();
        }
        return false;
    }
}
