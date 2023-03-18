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
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b5061027a806100206000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806312065fe01461005c57806327e235e314610076578063a87430ba1461009c578063a9059cbb146100c2578063cac7ed62146100f0575b600080fd5b6100646100f8565b60408051918252519081900360200190f35b6100646004803603602081101561008c57600080fd5b50356001600160a01b031661010b565b610064600480360360208110156100b257600080fd5b50356001600160a01b031661011d565b6100ee600480360360408110156100d857600080fd5b506001600160a01b03813516906020013561012f565b005b6100ee6101b8565b3360009081526020819052604090205490565b60006020819052908152604090205481565b60016020526000908152604090205481565b33600081815260208190526040902054821115610184576040805162461bcd60e51b815260206004820152600e60248201526d125b9d985b1a5908185b5bdd5b9d60921b604482015290519081900360640190fd5b6001600160a01b03908116600090815260208190526040808220805485900390559390911681529190912080549091019055565b336000818152600160205260409020541561021a576040805162461bcd60e51b815260206004820152601860248201527f4163636f756e7420616c72656164792072656769737465720000000000000000604482015290519081900360640190fd5b6001600160a01b031660009081526001602081815260408084209290925582905290206103e8905556fea2646970667358221220a4c4a2242b2f7f26fe362ea78f4f9c139fa453446c0ba09bb967460741587c2764736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b5061027c806100206000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80632ce271ab1461005c578063684b4289146100665780636904e9651461009e578063c0f25f43146100ca578063e3f36d63146100d2575b600080fd5b6100646100f8565b005b61008c6004803603602081101561007c57600080fd5b50356001600160a01b0316610185565b60408051918252519081900360200190f35b610064600480360360408110156100b457600080fd5b506001600160a01b038135169060200135610197565b61008c610221565b61008c600480360360208110156100e857600080fd5b50356001600160a01b0316610234565b336000818152600160205260409020541561015b5760408051636381e58960e11b815260206004820152601860248201527f4163636f756e7420616c72656164792072656769737465720000000000000000604482015290519081900360640190fd5b6001600160a01b031660009081526001602081815260408084209290925582905290206103e89055565b60016020526000908152604090205481565b336000818152602081905260409020548211156101ed5760408051636381e58960e11b815260206004820152600e60248201526d125b9d985b1a5908185b5bdd5b9d60921b604482015290519081900360640190fd5b6001600160a01b03908116600090815260208190526040808220805485900390559390911681529190912080549091019055565b3360009081526020819052604090205490565b6000602081905290815260409020548156fea264697066735822122004995185abeee97ee2017de62f7a0254da9a673d37f02f98a4a3a90483c3604964736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"balances\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getBalance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"registerAsset\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"users\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_BALANCES = "balances";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_REGISTERASSET = "registerAsset";

    public static final String FUNC_TRANSFER = "transfer";

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

    public BigInteger getBalance() throws ContractException {
        final Function function = new Function(FUNC_GETBALANCE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt registerAsset() {
        final Function function = new Function(
                FUNC_REGISTERASSET,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] registerAsset(TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REGISTERASSET,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRegisterAsset() {
        final Function function = new Function(
                FUNC_REGISTERASSET,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public TransactionReceipt transfer(String recipient, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(recipient),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] transfer(String recipient, BigInteger amount, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(recipient),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForTransfer(String recipient, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(recipient),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getTransferInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_TRANSFER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue()
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