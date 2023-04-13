package com.liujiahui.www;

import com.liujiahui.www.service.wrapper.*;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;

import java.math.BigInteger;

/**
 * 部署
 *
 * @author 刘家辉
 * @date 2023/04/13
 */
public class Deploy {
    public static void main(String[] args) throws Exception {
        BcosSDK sdk = BcosSDK.build("config-example.toml");
        Client client = sdk.getClient(1);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        String hexPrivateKey = cryptoKeyPair.getHexPrivateKey();
        ContractVerifierService deploy4 = ContractVerifierService.deploy(client, cryptoKeyPair);
        ContractTokenService deploy2 = ContractTokenService.deploy(client, cryptoKeyPair, "topview", BigInteger.valueOf(1), "TV");
        System.out.println(cryptoKeyPair.getAddress());
        System.out.println("hexPrivateKey = " + hexPrivateKey);
        ContractStorageService deploy = ContractStorageService.deploy(client, cryptoKeyPair, deploy2.getContractAddress());
        ContractMarketService deploy1 = ContractMarketService.deploy(client, cryptoKeyPair, deploy.getContractAddress(), deploy4.getContractAddress());
        ContractProxyService deploy3 = ContractProxyService.deploy(client, cryptoKeyPair, deploy1.getContractAddress(), deploy.getContractAddress(), deploy4.getContractAddress());
        System.out.println("deploy3.getContractAddress() = " + deploy3.getContractAddress());
    }
}
