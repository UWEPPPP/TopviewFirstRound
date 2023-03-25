package com.liujiahui.www.service.impl;

import com.liujiahui.www.entity.dto.TraceAccountOnContractDTO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.service.TraceContractService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
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
public class TraceContractServiceImpl implements TraceContractService {
    @Override
    public BigInteger getBalance(String privateKey) throws ContractException {
            BcosSDK sdk = BcosSDK.build("config-example.toml");
            Client client = sdk.getClient(1);
            CryptoSuite cryptoSuite = client.getCryptoSuite();
            CryptoKeyPair keyPair = cryptoSuite.createKeyPair(privateKey);
            TransactionDecoderInterface decoder = new TransactionDecoderService(cryptoSuite);
            ContractTradeService asset = ContractTradeService.load("0xe8c858630ba78eb2a70d2b492637d9d772fa0c03", client, keyPair);
            TraceInformationSaveDTO userInformationSaveDTO = TraceInformationSaveDTO.getInstance();
            userInformationSaveDTO.setDecoder(decoder);
            userInformationSaveDTO.setItemTradeSolidity(asset);
            return asset.getBalance();
        }

    @Override
    public TraceAccountOnContractDTO initByContract(String table) {
            BcosSDK sdk = BcosSDK.build("config-example.toml");
            Client client = sdk.getClient(1);
            CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
            String accountAddress = cryptoKeyPair.getAddress();
            ContractTradeService asset = ContractTradeService.load("0xe8c858630ba78eb2a70d2b492637d9d772fa0c03", client, cryptoKeyPair);
            String identity = "suppliers";
            if (table.equals(identity)) {
                asset.registerAsset(BigInteger.valueOf(1));
            } else {
                asset.registerAsset(BigInteger.valueOf(2));
            }
            TraceAccountOnContractDTO traceAccountOnContractDTO = new TraceAccountOnContractDTO();
            traceAccountOnContractDTO.setAccountAddress(accountAddress);
            traceAccountOnContractDTO.setPrivateKey(cryptoKeyPair.getHexPrivateKey());
            return traceAccountOnContractDTO;
    }
}

