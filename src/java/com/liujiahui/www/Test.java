package com.liujiahui.www;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;

public class Test {
    public static void main(String[] args) {
    }
    public void testMain() {
        BcosSDK bcosSDK = BcosSDK.build("config-example.toml");
        BcosSDK sdk =  BcosSDK.build("config-example.toml");
        Client client = sdk.getClient(1);
        System.out.println(client.getCryptoSuite().getCryptoKeyPair());
    }
}
