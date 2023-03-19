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
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b5061020a806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806327e235e314610051578063a87430ba14610089578063d312998f146100af578063f8b2cb4f146100d7575b600080fd5b6100776004803603602081101561006757600080fd5b50356001600160a01b03166100fd565b60408051918252519081900360200190f35b6100776004803603602081101561009f57600080fd5b50356001600160a01b031661010f565b6100d5600480360360208110156100c557600080fd5b50356001600160a01b0316610121565b005b610077600480360360208110156100ed57600080fd5b50356001600160a01b03166101b9565b60006020819052908152604090205481565b60016020526000908152604090205481565b6001600160a01b03811660009081526001602052604090205481901561018e576040805162461bcd60e51b815260206004820152601860248201527f4163636f756e7420616c72656164792072656769737465720000000000000000604482015290519081900360640190fd5b6001600160a01b031660009081526001602081815260408084209290925582905290206103e8905550565b6001600160a01b03166000908152602081905260409020549056fea26469706673582212202b5c3f65ed8264648a62cb97bef09816d475ec6bc3c6983251f70c9ddcbca60864736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b5061020b806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80630c01a4c4146100515780633f1baf8414610079578063684b4289146100b1578063e3f36d63146100d7575b600080fd5b6100776004803603602081101561006757600080fd5b50356001600160a01b03166100fd565b005b61009f6004803603602081101561008f57600080fd5b50356001600160a01b0316610196565b60408051918252519081900360200190f35b61009f600480360360208110156100c757600080fd5b50356001600160a01b03166101b1565b61009f600480360360208110156100ed57600080fd5b50356001600160a01b03166101c3565b6001600160a01b03811660009081526001602052604090205481901561016b5760408051636381e58960e11b815260206004820152601860248201527f4163636f756e7420616c72656164792072656769737465720000000000000000604482015290519081900360640190fd5b6001600160a01b031660009081526001602081815260408084209290925582905290206103e8905550565b6001600160a01b031660009081526020819052604090205490565b60016020526000908152604090205481565b6000602081905290815260409020548156fea26469706673582212205583342c1cd4b8e7a4b4771b7ef163328a8d78cda61273ae63336bcb6c3c4ebb64736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"balances\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"getBalance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"registerAsset\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"users\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_BALANCES = "balances";

    public static final String FUNC_GETBALANCE = "getBalance";

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

    public BigInteger getBalance(String user) throws ContractException {
        final Function function = new Function(FUNC_GETBALANCE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
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