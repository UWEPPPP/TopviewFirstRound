package com.liujiahui.www.service;

import com.liujiahui.www.AssetSolidity;
import com.liujiahui.www.entity.dto.UserAccountOnContractDTO;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;

/**
 * 合同登记服务
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class ContractRegisterService {
    public static UserAccountOnContractDTO initByContract() {
        BcosSDK bcosSDK = BcosSDK.build("config-example.toml");
        Client client= bcosSDK.getClient(1);
        // 随机生成非国密公私钥对
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        // 获取账户地址
        String accountAddress = cryptoKeyPair.getAddress();
        AssetSolidity asset = AssetSolidity.load("0x5672e6a652dd9a1aa5fc07e3bbd265557b009d22",client, cryptoKeyPair);
        asset.registerAsset();
        UserAccountOnContractDTO userAccountOnContractDTO = new UserAccountOnContractDTO();
        userAccountOnContractDTO.setAccountAddress(accountAddress);
        userAccountOnContractDTO.setPrivateKey(cryptoKeyPair.getHexPrivateKey());
        return userAccountOnContractDTO;
    }

}
