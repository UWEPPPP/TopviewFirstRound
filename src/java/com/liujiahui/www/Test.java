package com.liujiahui.www;

import com.liujiahui.www.service.wrapper.ContractAssetService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

public class Test {
    public static void main(String[] args) throws ContractException {
        BcosSDK sdk = BcosSDK.build("config-example.toml");
        Client client = sdk.getClient(1);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        String accountAddress = cryptoKeyPair.getAddress();
        System.out.println(accountAddress);
        ContractAssetService deploy = ContractAssetService.deploy(client, cryptoKeyPair);
        ContractTradeService asset = ContractTradeService.deploy(client, cryptoKeyPair, deploy.getContractAddress());
        System.out.println(asset.getContractAddress());


    }
}