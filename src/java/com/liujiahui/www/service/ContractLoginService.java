package com.liujiahui.www.service;

import com.liujiahui.www.AssetSolidity;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.math.BigInteger;

/**
 * 合同登录服务
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class ContractLoginService {
    public static BigInteger getBalance(String accountAddress, String privateKey) throws ContractException {
        BcosSDK bcosSDK = BcosSDK.build("config-example.toml");
        Client client= bcosSDK.getClient(1);
        // 随机生成非国密公私钥对
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair(privateKey);
        AssetSolidity asset = AssetSolidity.load("0x5672e6a652dd9a1aa5fc07e3bbd265557b009d22",client, cryptoKeyPair);
        return asset.balances(accountAddress);
    }
}
