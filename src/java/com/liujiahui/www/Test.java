package com.liujiahui.www;

import com.liujiahui.www.service.wrapper.ContractStorageService;
import com.liujiahui.www.service.wrapper.ContractTokenService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

public class Test {
    public static void main(String[] args) throws ContractException, IOException, InvalidKeySpecException {
        BcosSDK sdk = BcosSDK.build("config-example.toml");
        Client client = sdk.getClient(1);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        String hexPrivateKey = cryptoKeyPair.getHexPrivateKey();
        ContractTokenService deploy2 = ContractTokenService.deploy(client, cryptoKeyPair, 100000, "topview", 1, "TV");
        System.out.println(cryptoKeyPair.getAddress());
        System.out.println("hexPrivateKey = " + hexPrivateKey);
        ContractStorageService deploy = ContractStorageService.deploy(client, cryptoKeyPair, deploy2.getContractAddress());
        ContractTradeService deploy1 = ContractTradeService.deploy(client, cryptoKeyPair, deploy.getContractAddress());
        System.out.println(deploy1.getContractAddress());
    }
}
