package com.liujiahui.www.service;

import com.liujiahui.www.entity.dto.UserAccountOnContractDTO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.solidity.ItemTradeSolidity;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
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
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair(privateKey);
        ItemTradeSolidity asset = ItemTradeSolidity.load("0x5f39d4a82908f207a8519f89bb86abd3c3e863da",client, cryptoKeyPair);
        UserInformationSaveDTO userInformationSaveDTO = UserInformationSaveDTO.getInstance();
        userInformationSaveDTO.setItemTradeSolidity(asset);
        return asset.getBalance();
    }
    public static UserAccountOnContractDTO initByContract() {
        BcosSDK bcosSDK = BcosSDK.build("config-example.toml");
        Client client = bcosSDK.getClient(1);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        String accountAddress = cryptoKeyPair.getAddress();
        ItemTradeSolidity asset = ItemTradeSolidity.load("0x5f39d4a82908f207a8519f89bb86abd3c3e863da", client, cryptoKeyPair);
        asset.registerAsset();
        UserAccountOnContractDTO userAccountOnContractDTO = new UserAccountOnContractDTO();
        userAccountOnContractDTO.setAccountAddress(accountAddress);
        userAccountOnContractDTO.setPrivateKey(cryptoKeyPair.getHexPrivateKey());
        return userAccountOnContractDTO;
    }
}
