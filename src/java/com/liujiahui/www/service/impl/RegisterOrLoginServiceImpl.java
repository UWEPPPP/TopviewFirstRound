package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.factory.TraceFactoryDAO;
import com.liujiahui.www.dao.impl.*;
import com.liujiahui.www.entity.bo.TraceLoginBO;
import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.dto.TraceAccountOnContractDTO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.entity.po.UserPO;
import com.liujiahui.www.service.RegisterOrLoginService;
import com.liujiahui.www.service.wrapper.ContractProxyService;
import com.liujiahui.www.util.CryptoUtil;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderService;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author 刘家辉
 * @date 2023/04/04
 */
public class RegisterOrLoginServiceImpl implements RegisterOrLoginService {

    private RegisterOrLoginServiceImpl() {
    }

    public static RegisterOrLoginService getInstance() {
        return RegisterOrLoginInstance.INSTANCE;
    }
    private static final BcosSDK SDK = BcosSDK.build("config-example.toml");
    private static final Client CLIENT = SDK.getClient(1);
    private static final CryptoSuite CRYPTO_SUITE = CLIENT.getCryptoSuite();

    @Override
    public BigInteger loadByContract(String privateKey) {
        String hexPrivateKey;
        hexPrivateKey = CryptoUtil.decryptHexPrivateKey(privateKey, "src/resource/password.txt");
        CryptoKeyPair keyPair = CRYPTO_SUITE.createKeyPair(hexPrivateKey);
        //解密
        TransactionDecoderInterface decoder = new TransactionDecoderService(CRYPTO_SUITE);
        ContractProxyService asset = ContractProxyService.load("0xfe8c9734c6132566558ea403dfc96437ec1f0fd0", CLIENT, keyPair);
        UserSaveDTO userInformationSaveDTO = UserSaveDTO.getInstance();
        userInformationSaveDTO.setDecoder(decoder);
        userInformationSaveDTO.setItemTradeSolidity(asset);
        TransactionReceipt balance = asset.getBalance();
        Tuple1<BigInteger> getBalanceOutput = asset.getGetBalanceOutput(balance);
        return getBalanceOutput.getValue1();
    }

    @Override
    public TraceAccountOnContractDTO initByContract(String table) {
        CryptoKeyPair cryptoKeyPair = CRYPTO_SUITE.createKeyPair();
        String accountAddress = cryptoKeyPair.getAddress();
        ContractProxyService asset = ContractProxyService.load("0xfe8c9734c6132566558ea403dfc96437ec1f0fd0", CLIENT, cryptoKeyPair);
        String identity = "suppliers";
        if (table.equals(identity)) {
            asset.register(BigInteger.valueOf(1));
        } else {
            asset.register(BigInteger.valueOf(2));
        }
        String encryptHexPrivateKey;
        encryptHexPrivateKey = CryptoUtil.encryptHexPrivateKey(cryptoKeyPair.getHexPrivateKey(), "src/resource/password.txt");
        //加密
        TraceAccountOnContractDTO traceAccountOnContractDTO = new TraceAccountOnContractDTO();
        traceAccountOnContractDTO.setAccountAddress(accountAddress);
        traceAccountOnContractDTO.setPrivateKey(encryptHexPrivateKey);
        return traceAccountOnContractDTO;
    }




    @Override
    public UserSaveDTO login(TraceLoginBO traceLoginBO) {
        String userAccount = traceLoginBO.getAccount();
        String userPassword = traceLoginBO.getPassword();
        String identity = traceLoginBO.getIdentity();
        String identityCheck = Objects.equals(identity, "consumer") ? "buyer_account" : "seller_account";
        String judge = "buyer_account".equals(identityCheck) ? "consumer_is_read" : "supplier_is_read";
        UserPO login;
        String inform = "suppliers";
        userPassword = CryptoUtil.encryptHexPrivateKey(userPassword, "src/resource/password.txt");
        if (!Objects.equals(identity, inform)) {
            login = TraceFactoryDAO.getConsumerDAO().login(userAccount, userPassword);
        } else {
            login = TraceFactoryDAO.getSupplierDAO().login(userAccount, userPassword);
        }
        if (login != null) {
        UserSaveDTO user = UserSaveDTO.getInstance();
        String balance;
        balance = String.valueOf(loadByContract(login.getPrivateKey()));
        user.setUserName(login.getName());
        user.setBalance(balance);
        user.setGender(login.getGender());
        user.setPhone(login.getPhoneNumber());
        user.setIdentity(identity);
        user.setContractAccount(login.getAddress());
        if (identity.equals(inform)) {
            user.setInformationSize(new ConsumerFeedbackDAOImpl().getFeedbackNumber(login.getAddress()));
        }
        user.setAppealResultSize(new SupplierAppealDAOImpl().getResultAppealSize(login.getAddress(), identityCheck, judge));
        return user;
    }
        return null;
    }

    @Override
    public Boolean register(TraceRegisterBO traceRegisterBO) {
        String password = traceRegisterBO.getPassword();
        traceRegisterBO.setPassword(CryptoUtil.encryptHexPrivateKey(password, "src/resource/password.txt"));
        if (traceRegisterBO.getAddress() != null) {
            return new SupplierAccountDAOImpl().register(traceRegisterBO);
        } else {
            try {
                return new ConsumerAccountDAOImpl().register(traceRegisterBO);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private static class RegisterOrLoginInstance {
        private static final RegisterOrLoginServiceImpl INSTANCE = new RegisterOrLoginServiceImpl();
    }

}
