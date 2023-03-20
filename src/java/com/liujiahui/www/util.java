package com.liujiahui.www;

import com.liujiahui.www.solidity.AssetSolidity;
import com.liujiahui.www.solidity.ItemTradeSolidity;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.math.BigInteger;
import java.util.List;

public class util {
    public static void main(String[] args) throws ContractException {
        BcosSDK bcosSDK = BcosSDK.build("config-example.toml");
        Client client = bcosSDK.getClient(1);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        String accountAddress = cryptoKeyPair.getAddress();
        System.out.println(accountAddress);
        AssetSolidity deploy = AssetSolidity.deploy(client, cryptoKeyPair);
        ItemTradeSolidity asset = ItemTradeSolidity.deploy(client,cryptoKeyPair,deploy.getContractAddress());
        System.out.println(asset.getContractAddress());
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        ItemTradeSolidity load = ItemTradeSolidity.load(asset.getContractAddress(), client, keyPair);
      //  load.registerAsset();
        TransactionReceipt transactionReceipt1 = load.addItem("1", BigInteger.valueOf(21), "1");
        List<ItemTradeSolidity.NewItemAddEventResponse> newItemAddEvents = load.getNewItemAddEvents(transactionReceipt1);
        for (ItemTradeSolidity.NewItemAddEventResponse newItemAddEvent : newItemAddEvents) {
            System.out.println("1"+newItemAddEvent.name);
            System.out.println(newItemAddEvent.price);
        }
        CryptoKeyPair keyPair1 = client.getCryptoSuite().createKeyPair();
        ItemTradeSolidity load1 = ItemTradeSolidity.load(asset.getContractAddress(), client, keyPair1);
       // load1.registerAsset();
        System.out.println(load1.getBalance());
        TransactionReceipt transactionReceipt = load1.buyItem(keyPair.getAddress(), BigInteger.valueOf(0));
        List<ItemTradeSolidity.ItemSoldEventResponse> itemSoldEvents = load1.getItemSoldEvents(transactionReceipt);
        for (ItemTradeSolidity.ItemSoldEventResponse itemSoldEvent : itemSoldEvents) {
            System.out.println(itemSoldEvent.price);
            System.out.println(itemSoldEvent.buyer);
        }
    }
}
