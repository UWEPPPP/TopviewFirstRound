package com.liujiahui.www;

import com.liujiahui.www.solidity.AssetSolidity;
import com.liujiahui.www.solidity.ItemTrade;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.math.BigInteger;

public class Test {
    public static void main(String[] args) throws ContractException {
        BcosSDK bcosSDK = BcosSDK.build("config-example.toml");
        Client client = bcosSDK.getClient(1);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        String accountAddress = cryptoKeyPair.getAddress();
        System.out.println(accountAddress);
      AssetSolidity deploy = AssetSolidity.deploy(client, cryptoKeyPair);
       ItemTrade asset = ItemTrade.deploy(client, cryptoKeyPair, deploy.getContractAddress());
        System.out.println(asset.getContractAddress());

    }
}