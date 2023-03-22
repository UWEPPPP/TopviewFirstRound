package com.liujiahui.www.service;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

/**
 * 资产
 *
 * @author 刘家辉
 * @date 2023/03/22
 */
@SuppressWarnings("unchecked")
public class ContractAssetService extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506103cf806100206000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c8063b7785afb1161005b578063b7785afb14610127578063f2dc56a414610153578063f8b2cb4f14610179578063ff0569491461019f5761007d565b80631b4e7bd21461008257806327e235e3146100c15780635b86f599146100f9575b600080fd5b6100a86004803603602081101561009857600080fd5b50356001600160a01b03166101cb565b6040805192835260208301919091528051918290030190f35b6100e7600480360360208110156100d757600080fd5b50356001600160a01b03166101e4565b60408051918252519081900360200190f35b6101256004803603604081101561010f57600080fd5b506001600160a01b0381351690602001356101f6565b005b6101256004803603604081101561013d57600080fd5b506001600160a01b038135169060200135610218565b6100e76004803603602081101561016957600080fd5b50356001600160a01b031661030e565b6100e76004803603602081101561018f57600080fd5b50356001600160a01b031661035b565b610125600480360360408110156101b557600080fd5b506001600160a01b038135169060200135610376565b6001602081905260009182526040909120805491015482565b60006020819052908152604090205481565b6001600160a01b03909116600090815260208190526040902080549091019055565b6001600160a01b038216600090815260016020526040902054829015610285576040805162461bcd60e51b815260206004820152601860248201527f4163636f756e7420616c72656164792072656769737465720000000000000000604482015290519081900360640190fd5b6001600160a01b03811660009081526001602081905260409091208190558214156102cd576001600160a01b03811660009081526001602081905260409091208101556102ee565b6001600160a01b038116600090815260016020819052604090912060029101555b6001600160a01b031660009081526020819052604090206103e890555050565b6001600160a01b038116600090815260016020819052604082208101549081141561033d576001915050610356565b8060021415610350576002915050610356565b60009150505b919050565b6001600160a01b031660009081526020819052604090205490565b6001600160a01b039091166000908152602081905260409020805491909103905556fea2646970667358221220d10df4924f5dd0f6a63fe15be910339ec8086a501051cbf79679ba9d965ccb4564736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506103d3806100206000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c8063691bc1621161005b578063691bc162146101145780638ed469581461013a578063a965d52414610166578063e3f36d63146101a55761007d565b806311185c27146100825780633b04ba14146100b05780633f1baf84146100dc575b600080fd5b6100ae6004803603604081101561009857600080fd5b506001600160a01b0381351690602001356101cb565b005b6100ae600480360360408110156100c657600080fd5b506001600160a01b0381351690602001356101ee565b610102600480360360208110156100f257600080fd5b50356001600160a01b0316610210565b60408051918252519081900360200190f35b6101026004803603602081101561012a57600080fd5b50356001600160a01b031661022f565b6100ae6004803603604081101561015057600080fd5b506001600160a01b03813516906020013561027b565b61018c6004803603602081101561017c57600080fd5b50356001600160a01b0316610372565b6040805192835260208301919091528051918290030190f35b610102600480360360208110156101bb57600080fd5b50356001600160a01b031661038b565b6001600160a01b0390911660009081526020819052604090208054919091039055565b6001600160a01b03909116600090815260208190526040902080549091019055565b6001600160a01b0381166000908152602081905260409020545b919050565b6001600160a01b038116600090815260016020819052604082208101549081141561025e57600191505061022a565b806002141561027157600291505061022a565b600091505061022a565b6001600160a01b0382166000908152600160205260409020548290156102e95760408051636381e58960e11b815260206004820152601860248201527f4163636f756e7420616c72656164792072656769737465720000000000000000604482015290519081900360640190fd5b6001600160a01b0381166000908152600160208190526040909120819055821415610331576001600160a01b0381166000908152600160208190526040909120810155610352565b6001600160a01b038116600090815260016020819052604090912060029101555b6001600160a01b031660009081526020819052604090206103e890555050565b6001602081905260009182526040909120805491015482565b6000602081905290815260409020548156fea2646970667358221220576b8aa4470f76684230f66bacef11eb8adfeac2032d2c3351118885eaaac0e964736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"balances\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"decreaseBalance\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"getBalance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"increaseBalance\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"judgeIdentity\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"userAddress\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"chioce\",\"type\":\"uint256\"}],\"name\":\"registerAsset\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"userList\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"exist\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"identity\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_BALANCES = "balances";

    public static final String FUNC_DECREASEBALANCE = "decreaseBalance";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_INCREASEBALANCE = "increaseBalance";

    public static final String FUNC_JUDGEIDENTITY = "judgeIdentity";

    public static final String FUNC_REGISTERASSET = "registerAsset";

    public static final String FUNC_USERLIST = "userList";

    protected ContractAssetService(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public BigInteger balances(String param0) throws ContractException {
        final Function function = new Function(FUNC_BALANCES,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt decreaseBalance(String user, BigInteger amount) {
        final Function function = new Function(
                FUNC_DECREASEBALANCE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] decreaseBalance(String user, BigInteger amount, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_DECREASEBALANCE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForDecreaseBalance(String user, BigInteger amount) {
        final Function function = new Function(
                FUNC_DECREASEBALANCE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getDecreaseBalanceInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_DECREASEBALANCE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue()
        );
    }

    public BigInteger getBalance(String user) throws ContractException {
        final Function function = new Function(FUNC_GETBALANCE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt increaseBalance(String user, BigInteger amount) {
        final Function function = new Function(
                FUNC_INCREASEBALANCE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] increaseBalance(String user, BigInteger amount, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_INCREASEBALANCE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForIncreaseBalance(String user, BigInteger amount) {
        final Function function = new Function(
                FUNC_INCREASEBALANCE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getIncreaseBalanceInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_INCREASEBALANCE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue()
        );
    }

    public BigInteger judgeIdentity(String user) throws ContractException {
        final Function function = new Function(FUNC_JUDGEIDENTITY,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt registerAsset(String userAddress, BigInteger chioce) {
        final Function function = new Function(
                FUNC_REGISTERASSET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(userAddress),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(chioce)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] registerAsset(String userAddress, BigInteger chioce, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REGISTERASSET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(userAddress),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(chioce)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRegisterAsset(String userAddress, BigInteger chioce) {
        final Function function = new Function(
                FUNC_REGISTERASSET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(userAddress),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(chioce)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getRegisterAssetInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REGISTERASSET,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue()
        );
    }

    public Tuple2<BigInteger, BigInteger> userList(String param0) throws ContractException {
        final Function function = new Function(FUNC_USERLIST,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple2<BigInteger, BigInteger>(
                (BigInteger) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue());
    }

    public static ContractAssetService load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ContractAssetService(contractAddress, client, credential);
    }

    public static ContractAssetService deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(ContractAssetService.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}