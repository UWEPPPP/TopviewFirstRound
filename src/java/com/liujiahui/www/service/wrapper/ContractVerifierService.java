package com.liujiahui.www.service.wrapper;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class ContractVerifierService extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040526004805461ffff1916905534801561001b57600080fd5b5061087c8061002b6000396000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c8063817c1a2111610066578063817c1a21146102a357806384e2747b146102dd57806393087e5f146102e5578063c20922fa1461030b578063e852485b146103315761009e565b80634f447669146100a357806353bed806146100db57806357451a2114610113578063632adc2e146101c95780637fed4c401461027f575b600080fd5b6100c9600480360360208110156100b957600080fd5b50356001600160a01b031661035f565b60408051918252519081900360200190f35b610111600480360360608110156100f157600080fd5b506001600160a01b03813581169160208101359160409091013516610371565b005b6101116004803603604081101561012957600080fd5b6001600160a01b03823516919081019060408101602082013564010000000081111561015457600080fd5b82018360208201111561016657600080fd5b8035906020019184600183028401116401000000008311171561018857600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506103f6945050505050565b610111600480360360408110156101df57600080fd5b6001600160a01b03823516919081019060408101602082013564010000000081111561020a57600080fd5b82018360208201111561021c57600080fd5b8035906020019184600183028401116401000000008311171561023e57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610557945050505050565b6102876106b2565b604080516001600160a01b039092168252519081900360200190f35b6102c9600480360360208110156102b957600080fd5b50356001600160a01b03166106c1565b604080519115158252519081900360200190f35b6102876106df565b6102c9600480360360208110156102fb57600080fd5b50356001600160a01b03166106ee565b6100c96004803603602081101561032157600080fd5b50356001600160a01b0316610703565b6101116004803603604081101561034757600080fd5b506001600160a01b038135811691602001351661071e565b60066020526000908152604090205481565b60005481906001600160a01b038083169116148061039c57506001546001600160a01b038281169116145b6103d8576040805162461bcd60e51b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b50506001600160a01b03909116600090815260066020526040902055565b600454610100900460ff1661044857600080546001600160a01b0319166001600160a01b03841617905580516104339060029060208401906107ab565b506004805461ff001916610100179055610553565b600260405160200180828054600181600116156101000203166002900480156104a85780601f106104865761010080835404028352918201916104a8565b820191906000526020600020905b815481529060010190602001808311610494575b505091505060405160208183030381529060405280519060200120816040516020018082805190602001908083835b602083106104f65780518252601f1990920191602091820191016104d7565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405160208183030381529060405280519060200120141561055357600080546001600160a01b0319166001600160a01b0384161790555b5050565b60045460ff166105a257600180546001600160a01b0319166001600160a01b038416179055805161058f9060039060208401906107ab565b506004805460ff19166001179055610553565b600360405160200180828054600181600116156101000203166002900480156106025780601f106105e0576101008083540402835291820191610602565b820191906000526020600020905b8154815290600101906020018083116105ee575b505091505060405160208183030381529060405280519060200120816040516020018082805190602001908083835b602083106106505780518252601f199092019160209182019101610631565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405160208183030381529060405280519060200120141561055357600180546001600160a01b0384166001600160a01b03199091161790555050565b6000546001600160a01b031681565b6001600160a01b031660009081526005602052604090205460ff1690565b6001546001600160a01b031681565b60056020526000908152604090205460ff1681565b6001600160a01b031660009081526006602052604090205490565b60005481906001600160a01b038083169116148061074957506001546001600160a01b038281169116145b610785576040805162461bcd60e51b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b50506001600160a01b03166000908152600560205260409020805460ff19166001179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106107ec57805160ff1916838001178555610819565b82800160010185558215610819579182015b828111156108195782518255916020019190600101906107fe565b50610825929150610829565b5090565b61084391905b80821115610825576000815560010161082f565b9056fea26469706673582212204ab7b870753fc88af60fc543bad83fa54ad4acc5d862a912f6aa424f0d4fd75764736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040526004805461ffff1916905534801561001b57600080fd5b5061087e8061002b6000396000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c80639a9c70ed116100665780639a9c70ed146102955780639d1624e4146102cd5780639f8bfc01146102fb578063aa76e46014610331578063ba437ac5146103395761009e565b80630696f310146100a35780631ca4a4041461015b5780637bf1ed6f146101955780637c4740ad146101b9578063930479d21461026f575b600080fd5b610159600480360360408110156100b957600080fd5b6001600160a01b0382351691908101906040810160208201356401000000008111156100e457600080fd5b8201836020820111156100f657600080fd5b8035906020019184600183028401116401000000008311171561011857600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061035f945050505050565b005b6101816004803603602081101561017157600080fd5b50356001600160a01b03166104c0565b604080519115158252519081900360200190f35b61019d6104d5565b604080516001600160a01b039092168252519081900360200190f35b610159600480360360408110156101cf57600080fd5b6001600160a01b0382351691908101906040810160208201356401000000008111156101fa57600080fd5b82018360208201111561020c57600080fd5b8035906020019184600183028401116401000000008311171561022e57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506104e4945050505050565b6101816004803603602081101561028557600080fd5b50356001600160a01b031661063f565b6102bb600480360360208110156102ab57600080fd5b50356001600160a01b031661065d565b60408051918252519081900360200190f35b610159600480360360408110156102e357600080fd5b506001600160a01b0381358116916020013516610678565b6101596004803603606081101561031157600080fd5b506001600160a01b03813581169160208101359160409091013516610706565b61019d61078c565b6102bb6004803603602081101561034f57600080fd5b50356001600160a01b031661079b565b600454610100900460ff166103b157600080546001600160a01b0319166001600160a01b038416179055805161039c9060029060208401906107ad565b506004805461ff0019166101001790556104bc565b600260405160200180828054600181600116156101000203166002900480156104115780601f106103ef576101008083540402835291820191610411565b820191906000526020600020905b8154815290600101906020018083116103fd575b505091505060405160208183030381529060405280519060200120816040516020018082805190602001908083835b6020831061045f5780518252601f199092019160209182019101610440565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040528051906020012014156104bc57600080546001600160a01b0319166001600160a01b0384161790555b5050565b60056020526000908152604090205460ff1681565b6000546001600160a01b031681565b60045460ff1661052f57600180546001600160a01b0319166001600160a01b038416179055805161051c9060039060208401906107ad565b506004805460ff191660011790556104bc565b6003604051602001808280546001816001161561010002031660029004801561058f5780601f1061056d57610100808354040283529182019161058f565b820191906000526020600020905b81548152906001019060200180831161057b575b505091505060405160208183030381529060405280519060200120816040516020018082805190602001908083835b602083106105dd5780518252601f1990920191602091820191016105be565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040528051906020012014156104bc57600180546001600160a01b0384166001600160a01b03199091161790555050565b6001600160a01b031660009081526005602052604090205460ff1690565b6001600160a01b031660009081526006602052604090205490565b60005481906001600160a01b03808316911614806106a357506001546001600160a01b038281169116145b6106e05760408051636381e58960e11b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b50506001600160a01b03166000908152600560205260409020805460ff19166001179055565b60005481906001600160a01b038083169116148061073157506001546001600160a01b038281169116145b61076e5760408051636381e58960e11b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b50506001600160a01b03909116600090815260066020526040902055565b6001546001600160a01b031681565b60066020526000908152604090205481565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106107ee57805160ff191683800117855561081b565b8280016001018555821561081b579182015b8281111561081b578251825591602001919060010190610800565b5061082792915061082b565b5090565b61084591905b808211156108275760008155600101610831565b9056fea2646970667358221220f55bf2382aca2be81108851ac9f3387d50a2ab23f23035956db0f2e8fb9f992964736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"MarketRight\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"Market_right_check\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"choice\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"used_contract\",\"type\":\"address\"}],\"name\":\"Market_right_set\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"ProxyRight\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"Proxy_right_check\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"admin\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"used_contract\",\"type\":\"address\"}],\"name\":\"Proxy_right_set\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"market_address\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"},{\"internalType\":\"string\",\"name\":\"key\",\"type\":\"string\"}],\"name\":\"market_address_set\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"proxy_address\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"},{\"internalType\":\"string\",\"name\":\"key\",\"type\":\"string\"}],\"name\":\"proxy_address_set\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_MARKETRIGHT = "MarketRight";

    public static final String FUNC_MARKET_RIGHT_CHECK = "Market_right_check";

    public static final String FUNC_MARKET_RIGHT_SET = "Market_right_set";

    public static final String FUNC_PROXYRIGHT = "ProxyRight";

    public static final String FUNC_PROXY_RIGHT_CHECK = "Proxy_right_check";

    public static final String FUNC_PROXY_RIGHT_SET = "Proxy_right_set";

    public static final String FUNC_MARKET_ADDRESS = "market_address";

    public static final String FUNC_MARKET_ADDRESS_SET = "market_address_set";

    public static final String FUNC_PROXY_ADDRESS = "proxy_address";

    public static final String FUNC_PROXY_ADDRESS_SET = "proxy_address_set";

    protected ContractVerifierService(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public BigInteger MarketRight(String param0) throws ContractException {
        final Function function = new Function(FUNC_MARKETRIGHT,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public BigInteger Market_right_check(String user) throws ContractException {
        final Function function = new Function(FUNC_MARKET_RIGHT_CHECK,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt Market_right_set(String user, BigInteger choice, String used_contract) {
        final Function function = new Function(
                FUNC_MARKET_RIGHT_SET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(choice),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(used_contract)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] Market_right_set(String user, BigInteger choice, String used_contract, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_MARKET_RIGHT_SET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(choice),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(used_contract)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForMarket_right_set(String user, BigInteger choice, String used_contract) {
        final Function function = new Function(
                FUNC_MARKET_RIGHT_SET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(choice),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(used_contract)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple3<String, BigInteger, String> getMarket_right_setInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_MARKET_RIGHT_SET,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, BigInteger, String>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue(),
                (String) results.get(2).getValue()
        );
    }

    public Boolean ProxyRight(String param0) throws ContractException {
        final Function function = new Function(FUNC_PROXYRIGHT,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public Boolean Proxy_right_check(String user) throws ContractException {
        final Function function = new Function(FUNC_PROXY_RIGHT_CHECK,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public TransactionReceipt Proxy_right_set(String admin, String used_contract) {
        final Function function = new Function(
                FUNC_PROXY_RIGHT_SET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(admin),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(used_contract)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] Proxy_right_set(String admin, String used_contract, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_PROXY_RIGHT_SET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(admin),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(used_contract)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForProxy_right_set(String admin, String used_contract) {
        final Function function = new Function(
                FUNC_PROXY_RIGHT_SET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(admin),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(used_contract)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, String> getProxy_right_setInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_PROXY_RIGHT_SET,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(),
                (String) results.get(1).getValue()
        );
    }

    public String market_address() throws ContractException {
        final Function function = new Function(FUNC_MARKET_ADDRESS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt market_address_set(String addr, String key) {
        final Function function = new Function(
                FUNC_MARKET_ADDRESS_SET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(addr),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(key)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] market_address_set(String addr, String key, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_MARKET_ADDRESS_SET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(addr),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(key)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForMarket_address_set(String addr, String key) {
        final Function function = new Function(
                FUNC_MARKET_ADDRESS_SET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(addr),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(key)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, String> getMarket_address_setInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_MARKET_ADDRESS_SET,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(),
                (String) results.get(1).getValue()
        );
    }

    public String proxy_address() throws ContractException {
        final Function function = new Function(FUNC_PROXY_ADDRESS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt proxy_address_set(String addr, String key) {
        final Function function = new Function(
                FUNC_PROXY_ADDRESS_SET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(addr),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(key)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] proxy_address_set(String addr, String key, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_PROXY_ADDRESS_SET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(addr),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(key)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForProxy_address_set(String addr, String key) {
        final Function function = new Function(
                FUNC_PROXY_ADDRESS_SET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(addr),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(key)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, String> getProxy_address_setInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_PROXY_ADDRESS_SET,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(),
                (String) results.get(1).getValue()
        );
    }

    public static ContractVerifierService load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ContractVerifierService(contractAddress, client, credential);
    }

    public static ContractVerifierService deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(ContractVerifierService.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}