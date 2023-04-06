package com.liujiahui.www;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {
    public static void main(String[] args) {
//        BcosSDK sdk = BcosSDK.build("config-example.toml");
//        Client client = sdk.getClient(1);
//        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
//        String hexPrivateKey = cryptoKeyPair.getHexPrivateKey();
//        ContractTokenService deploy2 = ContractTokenService.deploy(client, cryptoKeyPair, 100000, "topview", 1, "TV");
//        System.out.println(cryptoKeyPair.getAddress());
//        System.out.println("hexPrivateKey = " + hexPrivateKey);
//        ContractStorageService deploy = ContractStorageService.deploy(client, cryptoKeyPair, deploy2.getContractAddress());
//        ContractTradeService deploy1 = ContractTradeService.deploy(client, cryptoKeyPair, deploy.getContractAddress());
//        System.out.println(deploy1.getContractAddress());
        Logger logger = Logger.getLogger("Start");
        try {
            throw new IOException();
        } catch (IOException e) {
            logger.warning("IOException");
            logger.log(Level.WARNING, "IOException");
        }
        System.out.println("??");

    }
}
