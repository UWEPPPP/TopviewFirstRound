package com.liujiahui.www.solidity;

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
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class AssetSolidity extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506102bd806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806327e235e3146100675780635b86f5991461009f578063a87430ba146100cd578063d312998f146100f3578063f8b2cb4f14610119578063ff0569491461013f575b600080fd5b61008d6004803603602081101561007d57600080fd5b50356001600160a01b031661016b565b60408051918252519081900360200190f35b6100cb600480360360408110156100b557600080fd5b506001600160a01b03813516906020013561017d565b005b61008d600480360360208110156100e357600080fd5b50356001600160a01b031661019f565b6100cb6004803603602081101561010957600080fd5b50356001600160a01b03166101b1565b61008d6004803603602081101561012f57600080fd5b50356001600160a01b0316610249565b6100cb6004803603604081101561015557600080fd5b506001600160a01b038135169060200135610264565b60006020819052908152604090205481565b6001600160a01b03909116600090815260208190526040902080549091019055565b60016020526000908152604090205481565b6001600160a01b03811660009081526001602052604090205481901561021e576040805162461bcd60e51b815260206004820152601860248201527f4163636f756e7420616c72656164792072656769737465720000000000000000604482015290519081900360640190fd5b6001600160a01b031660009081526001602081815260408084209290925582905290206103e8905550565b6001600160a01b031660009081526020819052604090205490565b6001600160a01b039091166000908152602081905260409020805491909103905556fea2646970667358221220c256fd714facc8aff91a6479603f63df76d152e309b8bb8d4e616d1e6c3ab07164736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506102be806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80630c01a4c41461006757806311185c271461008f5780633b04ba14146100bb5780633f1baf84146100e7578063684b42891461011f578063e3f36d6314610145575b600080fd5b61008d6004803603602081101561007d57600080fd5b50356001600160a01b031661016b565b005b61008d600480360360408110156100a557600080fd5b506001600160a01b038135169060200135610204565b61008d600480360360408110156100d157600080fd5b506001600160a01b038135169060200135610227565b61010d600480360360208110156100fd57600080fd5b50356001600160a01b0316610249565b60408051918252519081900360200190f35b61010d6004803603602081101561013557600080fd5b50356001600160a01b0316610264565b61010d6004803603602081101561015b57600080fd5b50356001600160a01b0316610276565b6001600160a01b0381166000908152600160205260409020548190156101d95760408051636381e58960e11b815260206004820152601860248201527f4163636f756e7420616c72656164792072656769737465720000000000000000604482015290519081900360640190fd5b6001600160a01b031660009081526001602081815260408084209290925582905290206103e8905550565b6001600160a01b0390911660009081526020819052604090208054919091039055565b6001600160a01b03909116600090815260208190526040902080549091019055565b6001600160a01b031660009081526020819052604090205490565b60016020526000908152604090205481565b6000602081905290815260409020548156fea2646970667358221220f4a164ef70ef578559c022c9e6ccba6b05c53c7418e73d1cc4d817ed0974543464736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"balances\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"decreaseBalance\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"getBalance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"increaseBalance\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"registerAsset\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"users\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_BALANCES = "balances";

    public static final String FUNC_DECREASEBALANCE = "decreaseBalance";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_INCREASEBALANCE = "increaseBalance";

    public static final String FUNC_REGISTERASSET = "registerAsset";

    public static final String FUNC_USERS = "users";

    protected AssetSolidity(String contractAddress, Client client, CryptoKeyPair credential) {
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

    public TransactionReceipt registerAsset(String user) {
        final Function function = new Function(
                FUNC_REGISTERASSET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] registerAsset(String user, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REGISTERASSET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRegisterAsset(String user) {
        final Function function = new Function(
                FUNC_REGISTERASSET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getRegisterAssetInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REGISTERASSET,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
        );
    }

    public BigInteger users(String param0) throws ContractException {
        final Function function = new Function(FUNC_USERS,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public static AssetSolidity load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new AssetSolidity(contractAddress, client, credential);
    }

    public static AssetSolidity deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(AssetSolidity.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}