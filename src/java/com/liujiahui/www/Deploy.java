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
        System.out.println("hexPrivateKey = " + hexPrivateKey);
        ContractStorageService deploy = ContractStorageService.deploy(client, cryptoKeyPair);
        ContractMarketService deploy1 = ContractMarketService.deploy(client, cryptoKeyPair, deploy.getContractAddress(), deploy4.getContractAddress(), deploy2.getContractAddress());
        ContractProxyService deploy3 = ContractProxyService.deploy(client, cryptoKeyPair, deploy1.getContractAddress(), deploy.getContractAddress(), deploy4.getContractAddress(), deploy2.getContractAddress());
        System.out.println("Token合约地址 = " + deploy2.getContractAddress());
        System.out.println("Storage合约地址 = " + deploy.getContractAddress());
        System.out.println("Verifier合约地址 = " + deploy4.getContractAddress());
        System.out.println("Market合约地址 = " + deploy1.getContractAddress());
        System.out.println("Proxy合约地址 = " + deploy3.getContractAddress());
    }
}
