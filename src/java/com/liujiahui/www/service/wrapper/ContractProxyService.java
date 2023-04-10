package com.liujiahui.www.service.wrapper;

import org.fisco.bcos.sdk.abi.FunctionEncoder;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class ContractProxyService extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040526001805460ff60a01b1916905534801561001d57600080fd5b5060405161059e38038061059e83398101604081905261003c9161020b565b6001600160a01b03831661006b5760405162461bcd60e51b8152600401610062906102b9565b60405180910390fd5b6001600160a01b0382166100915760405162461bcd60e51b8152600401610062906102b9565b600380546001600160a01b038086166001600160a01b0319928316179092556000805485841692169190911790819055604051632ea3cdd960e11b8152911690635d479bb2906100e590309060040161028f565b600060405180830381600087803b1580156100ff57600080fd5b505af1158015610113573d6000803e3d6000fd5b5050600480546001600160a01b038086166001600160a01b031992831617808455600280549093163017928390556040516357451a2160e01b815290821695506357451a2194506101699392909116910161028f565b600060405180830381600087803b15801561018357600080fd5b505af1158015610197573d6000803e3d6000fd5b50506004805460025460405163e852485b60e01b81526001600160a01b03928316955063e852485b94506101d19333939092169101610275565b600060405180830381600087803b1580156101eb57600080fd5b505af11580156101ff573d6000803e3d6000fd5b505050505050506102fa565b60008060006060848603121561021f578283fd5b835161022a816102e2565b602085015190935061023b816102e2565b604085015190925061024c816102e2565b809150509250925092565b600b81526a6c69756a6961687569315960a81b602082015260400190565b6001600160a01b0392831681529116602082015260400190565b6001600160a01b03821681526040602082018190526000906102b2908301610257565b9392505050565b6020808252600f908201526e496e76616c6964206164647265737360881b604082015260600190565b6001600160a01b03811681146102f757600080fd5b50565b610295806103096000396000f3fe6080604052600436106100385760003560e01c80630900f0101461005c57806359679b0f1461007c578063f851a440146100a757610047565b36610047576100456100bc565b005b34801561005357600080fd5b506100456100bc565b34801561006857600080fd5b506100456100773660046101db565b6100d3565b34801561008857600080fd5b50610091610199565b60405161009e9190610229565b60405180910390f35b3480156100b357600080fd5b506100916101a8565b6003546100d1906001600160a01b03166101b7565b565b6004805460405163817c1a2160e01b81526001600160a01b039091169163817c1a219161010291339101610229565b60206040518083038186803b15801561011a57600080fd5b505afa15801561012e573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101529190610209565b6101775760405162461bcd60e51b815260040161016e9061023d565b60405180910390fd5b600380546001600160a01b0319166001600160a01b0392909216919091179055565b6003546001600160a01b031681565b6001546001600160a01b031681565b3660008037600080366000845af43d6000803e8080156101d6573d6000f35b3d6000fd5b6000602082840312156101ec578081fd5b81356001600160a01b0381168114610202578182fd5b9392505050565b60006020828403121561021a578081fd5b81518015158114610202578182fd5b6001600160a01b0391909116815260200190565b602080825260089082015267139bc81c9a59da1d60c21b60408201526060019056fea2646970667358221220f3f8352a562d47a5b25a8adb1ab024c9a741363940e3260a403446ab9ac08b4864736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040526001805460ff60a01b1916905534801561001d57600080fd5b506040516105a03803806105a083398101604081905261003c9161020c565b6001600160a01b03831661006c57604051636381e58960e11b8152600401610063906102ba565b60405180910390fd5b6001600160a01b03821661009357604051636381e58960e11b8152600401610063906102ba565b600380546001600160a01b038086166001600160a01b031992831617909255600080548584169216919091179081905560405163651c699360e11b815291169063ca38d326906100e7903090600401610290565b600060405180830381600087803b15801561010157600080fd5b505af1158015610115573d6000803e3d6000fd5b5050600480546001600160a01b038086166001600160a01b0319928316178084556002805490931630179283905560405162696f3160e41b81529082169550630696f310945061016a93929091169101610290565b600060405180830381600087803b15801561018457600080fd5b505af1158015610198573d6000803e3d6000fd5b505060048054600254604051632745893960e21b81526001600160a01b039283169550639d1624e494506101d29333939092169101610276565b600060405180830381600087803b1580156101ec57600080fd5b505af1158015610200573d6000803e3d6000fd5b505050505050506102fb565b600080600060608486031215610220578283fd5b835161022b816102e3565b602085015190935061023c816102e3565b604085015190925061024d816102e3565b809150509250925092565b600b81526a6c69756a6961687569315960a81b602082015260400190565b6001600160a01b0392831681529116602082015260400190565b6001600160a01b03821681526040602082018190526000906102b3908301610258565b9392505050565b6020808252600f908201526e496e76616c6964206164647265737360881b604082015260600190565b6001600160a01b03811681146102f857600080fd5b50565b6102968061030a6000396000f3fe6080604052600436106100385760003560e01c8063344a77171461005c5780639b8376e414610087578063f1522800146100a757610047565b36610047576100456100bc565b005b34801561005357600080fd5b506100456100bc565b34801561006857600080fd5b506100716100d3565b60405161007e919061022a565b60405180910390f35b34801561009357600080fd5b506100456100a23660046101dc565b6100e2565b3480156100b357600080fd5b506100716101a9565b6003546100d1906001600160a01b03166101b8565b565b6003546001600160a01b031681565b600480546040516349823ce960e11b81526001600160a01b039091169163930479d2916101119133910161022a565b60206040518083038186803b15801561012957600080fd5b505afa15801561013d573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610161919061020a565b61018757604051636381e58960e11b815260040161017e9061023e565b60405180910390fd5b600380546001600160a01b0319166001600160a01b0392909216919091179055565b6001546001600160a01b031681565b3660008037600080366000845af43d6000803e8080156101d7573d6000f35b3d6000fd5b6000602082840312156101ed578081fd5b81356001600160a01b0381168114610203578182fd5b9392505050565b60006020828403121561021b578081fd5b81518015158114610203578182fd5b6001600160a01b0391909116815260200190565b602080825260089082015267139bc81c9a59da1d60c21b60408201526060019056fea2646970667358221220c8f3dbbaa42188348128f51f0d7b4f3aceb5806f11f052856f4bdb21532fe61164736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"implementation_\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"veri_Address\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"stateMutability\":\"nonpayable\",\"type\":\"fallback\"},{\"inputs\":[],\"name\":\"_implementation\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"admin\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"newCont\",\"type\":\"address\"}],\"name\":\"upgrade\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"stateMutability\":\"payable\",\"type\":\"receive\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC__IMPLEMENTATION = "_implementation";

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_UPGRADE = "upgrade";

    protected ContractProxyService(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static ContractProxyService load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ContractProxyService(contractAddress, client, credential);
    }

    public static ContractProxyService deploy(Client client, CryptoKeyPair credential, String implementation_, String addr, String veri_Address) throws ContractException {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(implementation_),
                new org.fisco.bcos.sdk.abi.datatypes.Address(addr),
                new org.fisco.bcos.sdk.abi.datatypes.Address(veri_Address)));
        return deploy(ContractProxyService.class, client, credential, getBinary(client.getCryptoSuite()), encodedConstructor);
    }

    public String _implementation() throws ContractException {
        final Function function = new Function(FUNC__IMPLEMENTATION,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public String admin() throws ContractException {
        final Function function = new Function(FUNC_ADMIN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt upgrade(String newCont) {
        final Function function = new Function(
                FUNC_UPGRADE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(newCont)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] upgrade(String newCont, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_UPGRADE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(newCont)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForUpgrade(String newCont) {
        final Function function = new Function(
                FUNC_UPGRADE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(newCont)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getUpgradeInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_UPGRADE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
        );
    }
}