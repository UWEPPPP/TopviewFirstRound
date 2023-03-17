package com.liujiahui.www;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

public class Testg {
    public static void main(String[] args) throws ContractException {

        BcosSDK bcosSDK = BcosSDK.build("config-example.toml");
        Client client= bcosSDK.getClient(1);
        // 随机生成非国密公私钥对
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().getKeyPairFactory().createKeyPair("e88f82721f37c931fb8844a55ad61b89cea76c165cf59f922b2102acb179eb88");
        // 获取账户地址
        String accountAddress = cryptoKeyPair.getAddress();
        AssetSolidity asset = AssetSolidity.deploy(client, cryptoKeyPair);
        System.out.println(asset.getContractAddress());

    }
}
