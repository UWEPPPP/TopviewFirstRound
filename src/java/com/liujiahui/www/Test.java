package com.liujiahui.www;

import com.liujiahui.www.service.wrapper.ContractAssetService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws ContractException, NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        BcosSDK sdk = BcosSDK.build("config-example.toml");
        Client client = sdk.getClient(1);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        String hexPrivateKey = cryptoKeyPair.getHexPrivateKey();
        ContractAssetService deploy = ContractAssetService.deploy(client, cryptoKeyPair);
        ContractTradeService deploy1 = ContractTradeService.deploy(client, cryptoKeyPair, deploy.getContractAddress());
        System.out.println(deploy1.getContractAddress());


    }
}
