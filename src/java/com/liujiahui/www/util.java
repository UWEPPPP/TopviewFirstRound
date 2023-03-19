package com.liujiahui.www;

import com.liujiahui.www.solidity.AssetSolidity;
import com.liujiahui.www.solidity.ItemTradeSolidity;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.math.BigInteger;

public class util {
    public static void main(String[] args) throws ContractException {
        BcosSDK bcosSDK = BcosSDK.build("config-example.toml");
        Client client = bcosSDK.getClient(1);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        String accountAddress = cryptoKeyPair.getAddress();
        ItemTradeSolidity asset = ItemTradeSolidity.load("0x87ed1ea99c3624a24db264c256e5ebc841b670a6", client, cryptoKeyPair);
        System.out.println(asset.items("0xbdd56639c2554b45b6dbd22dcebb054ed10fc9af", BigInteger.valueOf(0)));

    }
}
