package com.liujiahui.www;

import com.liujiahui.www.service.wrapper.ContractAssetService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Test {
    public static void main(String[] args) throws ContractException {
        BcosSDK sdk = BcosSDK.build("config-example.toml");
        Client client = sdk.getClient(1);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        String hexPrivateKey = cryptoKeyPair.getHexPrivateKey();
        ContractAssetService deploy = ContractAssetService.deploy(client, cryptoKeyPair);
        ContractTradeService deploy1 = ContractTradeService.deploy(client, cryptoKeyPair, deploy.getContractAddress());
        System.out.println(deploy1.getContractAddress());

    }
}