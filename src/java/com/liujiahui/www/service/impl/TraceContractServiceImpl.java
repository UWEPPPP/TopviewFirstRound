package com.liujiahui.www.service.impl;

import com.liujiahui.www.entity.dto.TraceAccountOnContractDTO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.service.TraceContractService;
import com.liujiahui.www.service.util.CryptoUtil;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 合同登录服务
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class TraceContractServiceImpl implements TraceContractService {
    @Override
    public BigInteger getBalance(String privateKey) throws ContractException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
            BcosSDK sdk = BcosSDK.build("config-example.toml");
            Client client = sdk.getClient(1);
            CryptoSuite cryptoSuite = client.getCryptoSuite();
            String hexPrivateKey = CryptoUtil.decryptHexPrivateKey(privateKey, "src/resource/password.txt");
            CryptoKeyPair keyPair = cryptoSuite.createKeyPair(hexPrivateKey);
            //解密
            TransactionDecoderInterface decoder = new TransactionDecoderService(cryptoSuite);
            ContractTradeService asset = ContractTradeService.load("0x325e5ef06a0bcb5d09d87e01beb4cd5eb7249971\n", client, keyPair);
            TraceInformationSaveDTO userInformationSaveDTO = TraceInformationSaveDTO.getInstance();
            userInformationSaveDTO.setDecoder(decoder);
            userInformationSaveDTO.setItemTradeSolidity(asset);
            return asset.getBalance();
        }

    @Override
    public TraceAccountOnContractDTO initByContract(String table) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
            BcosSDK sdk = BcosSDK.build("config-example.toml");
            Client client = sdk.getClient(1);
            CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
            String accountAddress = cryptoKeyPair.getAddress();
            ContractTradeService asset = ContractTradeService.load("0x325e5ef06a0bcb5d09d87e01beb4cd5eb7249971\n", client, cryptoKeyPair);
            String identity = "suppliers";
            if (table.equals(identity)) {
                asset.registerAsset(BigInteger.valueOf(1));
            } else {
                asset.registerAsset(BigInteger.valueOf(2));
            }
            String encryptHexPrivateKey = CryptoUtil.encryptHexPrivateKey(cryptoKeyPair.getHexPrivateKey(), "src/resource/password.txt");
            //加密
            TraceAccountOnContractDTO traceAccountOnContractDTO = new TraceAccountOnContractDTO();
            traceAccountOnContractDTO.setAccountAddress(accountAddress);
            traceAccountOnContractDTO.setPrivateKey(encryptHexPrivateKey);
            return traceAccountOnContractDTO;
    }
}

