package com.liujiahui.www.service.impl;

import com.liujiahui.www.entity.dto.TraceAccountOnContractDTO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.service.ContractInitService;
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
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.math.BigInteger;

/**
 * 合同登录服务
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class ContractInitServiceImpl implements ContractInitService {
    private static final BcosSDK SDK = BcosSDK.build("config-example.toml");
    private static final Client CLIENT = SDK.getClient(1);
    private static final CryptoSuite CRYPTO_SUITE = CLIENT.getCryptoSuite();

    @Override
    public BigInteger getBalance(String privateKey) {
        String hexPrivateKey;
        hexPrivateKey = CryptoUtil.decryptHexPrivateKey(privateKey, "src/resource/password.txt");
        CryptoKeyPair keyPair = CRYPTO_SUITE.createKeyPair(hexPrivateKey);
        //解密
        TransactionDecoderInterface decoder = new TransactionDecoderService(CRYPTO_SUITE);
        ContractProxyService asset = ContractProxyService.load("0xc26871383a3bf14fe2965e3b917c12c433ea3545", CLIENT, keyPair);
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
        ContractProxyService asset = ContractProxyService.load("0xc26871383a3bf14fe2965e3b917c12c433ea3545", CLIENT, cryptoKeyPair);
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
}

