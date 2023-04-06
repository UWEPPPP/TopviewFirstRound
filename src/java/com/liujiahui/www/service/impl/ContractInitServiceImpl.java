package com.liujiahui.www.service.impl;

import com.liujiahui.www.entity.dto.TraceAccountOnContractDTO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.service.ContractInitService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import com.liujiahui.www.util.CryptoUtil;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
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
        ContractTradeService asset = ContractTradeService.load("0x2a8e6f2d815a4e44de6d5377763228256a3e64d9", CLIENT, keyPair);
        UserSaveDTO userInformationSaveDTO = UserSaveDTO.getInstance();
        userInformationSaveDTO.setDecoder(decoder);
        userInformationSaveDTO.setItemTradeSolidity(asset);
        try {
            return asset.getBalance();
        } catch (ContractException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TraceAccountOnContractDTO initByContract(String table) {
        CryptoKeyPair cryptoKeyPair = CRYPTO_SUITE.createKeyPair();
        String accountAddress = cryptoKeyPair.getAddress();
        ContractTradeService asset = ContractTradeService.load("0x2a8e6f2d815a4e44de6d5377763228256a3e64d9", CLIENT, cryptoKeyPair);
        String identity = "suppliers";
        if (table.equals(identity)) {
            asset.registerAsset(BigInteger.valueOf(1));
        } else {
            asset.registerAsset(BigInteger.valueOf(2));
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

