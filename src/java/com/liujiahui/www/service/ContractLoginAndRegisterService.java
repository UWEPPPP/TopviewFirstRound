package com.liujiahui.www.service;

import com.liujiahui.www.entity.dto.UserAccountOnContractDTO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.solidity.ItemTrade;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.math.BigInteger;

/**
 * 合同登录服务
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class ContractLoginAndRegisterService {
    public static BigInteger getBalance(String privateKey) throws ContractException {
        BcosSDK bcosSDK = BcosSDK.build("config-example.toml");
        Client client= bcosSDK.getClient(1);
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        CryptoKeyPair keyPair = cryptoSuite.createKeyPair(privateKey);
        TransactionDecoderInterface decoder = new TransactionDecoderService(cryptoSuite);
        ItemTrade asset = ItemTrade.load("0xf92f75db5183c06a00a7515be0d1f965f1d1c649",client, keyPair);
        UserInformationSaveDTO userInformationSaveDTO = UserInformationSaveDTO.getInstance();
        userInformationSaveDTO.setDecoder(decoder);
        userInformationSaveDTO.setItemTradeSolidity(asset);
        return asset.getBalance();
    }
    public static UserAccountOnContractDTO initByContract(String table) {
        BcosSDK bcosSDK = BcosSDK.build("config-example.toml");
        Client client = bcosSDK.getClient(1);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        String accountAddress = cryptoKeyPair.getAddress();
        ItemTrade asset = ItemTrade.load("0xf92f75db5183c06a00a7515be0d1f965f1d1c649", client, cryptoKeyPair);
        String identity="suppliers";
        if(table.equals(identity)) {
            asset.registerAsset(BigInteger.valueOf(1));
        }else {
           asset.registerAsset(BigInteger.valueOf(2));
        }
        UserAccountOnContractDTO userAccountOnContractDTO = new UserAccountOnContractDTO();
        userAccountOnContractDTO.setAccountAddress(accountAddress);
        userAccountOnContractDTO.setPrivateKey(cryptoKeyPair.getHexPrivateKey());
        return userAccountOnContractDTO;
    }
}
