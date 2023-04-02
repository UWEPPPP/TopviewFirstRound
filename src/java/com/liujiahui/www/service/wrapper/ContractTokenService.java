package com.liujiahui.www.service.wrapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionEncoder;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint8;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class ContractTokenService extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50604051610aec380380610aec8339818101604052608081101561003357600080fd5b81516020830180516040519294929383019291908464010000000082111561005a57600080fd5b90830190602082018581111561006f57600080fd5b825164010000000081118282018810171561008957600080fd5b82525081516020918201929091019080838360005b838110156100b657818101518382015260200161009e565b50505050905090810190601f1680156100e35780820380516001836020036101000a031916815260200191505b5060408181526020830151920180519294919391928464010000000082111561010b57600080fd5b90830190602082018581111561012057600080fd5b825164010000000081118282018810171561013a57600080fd5b82525081516020918201929091019080838360005b8381101561016757818101518382015260200161014f565b50505050905090810190601f1680156101945780820380516001836020036101000a031916815260200191505b5060409081523360009081526020818152919020889055600288905586516101c59450600393509087019150610205565b506004805460ff191660ff841617905580516101e8906005906020840190610205565b5050600680546001600160a01b03191633179055506102a0915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061024657805160ff1916838001178555610273565b82800160010185558215610273579182015b82811115610273578251825591602001919060010190610258565b5061027f929150610283565b5090565b61029d91905b8082111561027f5760008155600101610289565b90565b61083d806102af6000396000f3fe608060405234801561001057600080fd5b50600436106100ea5760003560e01c80634420e4861161008c578063743b345211610066578063743b3452146102e857806395d89b4114610314578063a9059cbb1461031c578063dd62ed3e14610348576100ea565b80634420e4861461026e5780635c6581651461029457806370a08231146102c2576100ea565b806321670f22116100c857806321670f22146101c657806323b872dd146101f457806327e235e31461022a578063313ce56714610250576100ea565b806306fdde03146100ef578063095ea7b31461016c57806318160ddd146101ac575b600080fd5b6100f7610376565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610131578181015183820152602001610119565b50505050905090810190601f16801561015e5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101986004803603604081101561018257600080fd5b506001600160a01b038135169060200135610404565b604080519115158252519081900360200190f35b6101b4610471565b60408051918252519081900360200190f35b6101f2600480360360408110156101dc57600080fd5b506001600160a01b038135169060200135610477565b005b6101986004803603606081101561020a57600080fd5b506001600160a01b03813581169160208101359091169060400135610486565b6101b46004803603602081101561024057600080fd5b50356001600160a01b03166105c2565b6102586105d4565b6040805160ff9092168252519081900360200190f35b6101f26004803603602081101561028457600080fd5b50356001600160a01b03166105dd565b6101b4600480360360408110156102aa57600080fd5b506001600160a01b03813581169160200135166105f9565b6101b4600480360360208110156102d857600080fd5b50356001600160a01b0316610616565b6101f2600480360360408110156102fe57600080fd5b506001600160a01b038135169060200135610631565b6100f7610649565b6101986004803603604081101561033257600080fd5b506001600160a01b0381351690602001356106a4565b6101b46004803603604081101561035e57600080fd5b506001600160a01b0381358116916020013516610774565b6003805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103fc5780601f106103d1576101008083540402835291602001916103fc565b820191906000526020600020905b8154815290600101906020018083116103df57829003601f168201915b505050505081565b6001600160a01b03808316600081815260016020908152604080832060068054871685529083528184208790555481518781529151939516927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929181900390910190a350600192915050565b60025481565b61048182826106a4565b505050565b6001600160a01b0380841660008181526001602090815260408083206006549095168352938152838220549282528190529182205483118015906104ca5750828110155b6105055760405162461bcd60e51b81526004018080602001828103825260398152602001806107cf6039913960400191505060405180910390fd5b6001600160a01b038085166000908152602081905260408082208054870190559187168152208054849003905560001981101561056c576001600160a01b038086166000908152600160209081526040808320600654909416835292905220805484900390555b836001600160a01b0316856001600160a01b03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040518082815260200191505060405180910390a3506001949350505050565b60006020819052908152604090205481565b60045460ff1681565b6105e9816103e8610404565b506105f58160646106a4565b5050565b600160209081526000928352604080842090915290825290205481565b6001600160a01b031660009081526020819052604090205490565b6006546104819083906001600160a01b031683610486565b6005805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103fc5780601f106103d1576101008083540402835291602001916103fc565b6006546001600160a01b03166000908152602081905260408120548211156106fd5760405162461bcd60e51b815260040180806020018281038252602f8152602001806107a0602f913960400191505060405180910390fd5b600680546001600160a01b03908116600090815260208181526040808320805488900390558784168084529281902080548801905593548451878152945192949316927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929081900390910190a350600192915050565b6001600160a01b0391821660009081526001602090815260408083209390941682529190915220549056fe746f6b656e2062616c616e6365206973206c6f776572207468616e207468652076616c756520726571756573746564746f6b656e2062616c616e6365206f7220616c6c6f77616e6365206973206c6f776572207468616e20616d6f756e7420726571756573746564a2646970667358221220be903d5185c40d19d818ea5282066f9b2b33889156d684c34b280bbe2310620464736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50604051610aee380380610aee8339818101604052608081101561003357600080fd5b81516020830180516040519294929383019291908464010000000082111561005a57600080fd5b90830190602082018581111561006f57600080fd5b825164010000000081118282018810171561008957600080fd5b82525081516020918201929091019080838360005b838110156100b657818101518382015260200161009e565b50505050905090810190601f1680156100e35780820380516001836020036101000a031916815260200191505b5060408181526020830151920180519294919391928464010000000082111561010b57600080fd5b90830190602082018581111561012057600080fd5b825164010000000081118282018810171561013a57600080fd5b82525081516020918201929091019080838360005b8381101561016757818101518382015260200161014f565b50505050905090810190601f1680156101945780820380516001836020036101000a031916815260200191505b5060409081523360009081526020818152919020889055600288905586516101c59450600393509087019150610205565b506004805460ff191660ff841617905580516101e8906005906020840190610205565b5050600680546001600160a01b03191633179055506102a0915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061024657805160ff1916838001178555610273565b82800160010185558215610273579182015b82811115610273578251825591602001919060010190610258565b5061027f929150610283565b5090565b61029d91905b8082111561027f5760008155600101610289565b90565b61083f806102af6000396000f3fe608060405234801561001057600080fd5b50600436106100ea5760003560e01c8063852d92131161008c578063b11b688311610066578063b11b6883146102f4578063cc8be70e146102fc578063d4b0142614610322578063e3f36d6314610350576100ea565b8063852d92131461026a57806394ed4dfd14610298578063ad8a9731146102be576100ea565b806346b13615116100c857806346b13615146101775780635bfa27961461019557806360e639f3146102125780636904e9651461023e576100ea565b80630256e278146100ef5780631e7d6248146101095780631f2d486014610137575b600080fd5b6100f7610376565b60408051918252519081900360200190f35b6101356004803603604081101561011f57600080fd5b506001600160a01b03813516906020013561037c565b005b6101636004803603604081101561014d57600080fd5b506001600160a01b03813516906020013561038b565b604080519115158252519081900360200190f35b61017f6103f8565b6040805160ff9092168252519081900360200190f35b61019d610401565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101d75781810151838201526020016101bf565b50505050905090810190601f1680156102045780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101356004803603604081101561022857600080fd5b506001600160a01b03813516906020013561048f565b6101636004803603604081101561025457600080fd5b506001600160a01b0381351690602001356104a7565b6100f76004803603604081101561028057600080fd5b506001600160a01b0381358116916020013516610578565b610135600480360360208110156102ae57600080fd5b50356001600160a01b03166105a3565b610163600480360360608110156102d457600080fd5b506001600160a01b038135811691602081013590911690604001356105bf565b61019d6106fc565b6100f76004803603602081101561031257600080fd5b50356001600160a01b0316610757565b6100f76004803603604081101561033857600080fd5b506001600160a01b0381358116916020013516610772565b6100f76004803603602081101561036657600080fd5b50356001600160a01b031661078f565b60025481565b61038682826104a7565b505050565b6001600160a01b03808316600081815260016020908152604080832060068054871685529083528184208790555481518781529151939516927fd1e45707b3f71c77903b61f04c900f772db264b9bf618f1cc3308fb516eb6169929181900390910190a350600192915050565b60045460ff1681565b6005805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156104875780601f1061045c57610100808354040283529160200191610487565b820191906000526020600020905b81548152906001019060200180831161046a57829003601f168201915b505050505081565b6006546103869083906001600160a01b0316836105bf565b6006546001600160a01b031660009081526020819052604081205482111561050157604051636381e58960e11b815260040180806020018281038252602f8152602001806107a2602f913960400191505060405180910390fd5b600680546001600160a01b03908116600090815260208181526040808320805488900390558784168084529281902080548801905593548451878152945192949316927f18f84334255a242551aa98c68047b5da8063eab9fbeaec1eddeea280044b9ff1929081900390910190a350600192915050565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b6105af816103e861038b565b506105bb8160646104a7565b5050565b6001600160a01b0380841660008181526001602090815260408083206006549095168352938152838220549282528190529182205483118015906106035750828110155b61063f57604051636381e58960e11b81526004018080602001828103825260398152602001806107d16039913960400191505060405180910390fd5b6001600160a01b03808516600090815260208190526040808220805487019055918716815220805484900390556000198110156106a6576001600160a01b038086166000908152600160209081526040808320600654909416835292905220805484900390555b836001600160a01b0316856001600160a01b03167f18f84334255a242551aa98c68047b5da8063eab9fbeaec1eddeea280044b9ff1856040518082815260200191505060405180910390a3506001949350505050565b6003805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156104875780601f1061045c57610100808354040283529160200191610487565b6001600160a01b031660009081526020819052604090205490565b600160209081526000928352604080842090915290825290205481565b6000602081905290815260409020548156fe746f6b656e2062616c616e6365206973206c6f776572207468616e207468652076616c756520726571756573746564746f6b656e2062616c616e6365206f7220616c6c6f77616e6365206973206c6f776572207468616e20616d6f756e7420726571756573746564a2646970667358221220fcd54511a527074b2c475424d8e995f8d6a342bef73446ada4df70f173831efe64736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_initialAmount\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"_tokenName\",\"type\":\"string\"},{\"internalType\":\"uint8\",\"name\":\"_decimalUnits\",\"type\":\"uint8\"},{\"internalType\":\"string\",\"name\":\"_tokenSymbol\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_spender\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_spender\",\"type\":\"address\"}],\"name\":\"allowance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"remaining\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"allowed\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"balance\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"balances\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"decimals\",\"outputs\":[{\"internalType\":\"uint8\",\"name\":\"\",\"type\":\"uint8\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"supplier\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"counter\",\"type\":\"uint256\"}],\"name\":\"pledge\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"register\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"supplier\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"count\",\"type\":\"uint256\"}],\"name\":\"reward\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_ALLOWED = "allowed";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BALANCES = "balances";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_PLEDGE = "pledge";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_REWARD = "reward";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final Event APPROVAL_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    protected ContractTokenService(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeApprovalEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(APPROVAL_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeApprovalEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(APPROVAL_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeTransferEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(TRANSFER_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeTransferEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(TRANSFER_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public BigInteger allowance(String _owner, String _spender) throws ContractException {
        final Function function = new Function(FUNC_ALLOWANCE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_owner),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(_spender)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public BigInteger allowed(String param0, String param1) throws ContractException {
        final Function function = new Function(FUNC_ALLOWED,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(param0),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt approve(String owner, BigInteger _value) {
        final Function function = new Function(
                FUNC_APPROVE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(owner),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] approve(String owner, BigInteger _value, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_APPROVE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(owner),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForApprove(String owner, BigInteger _value) {
        final Function function = new Function(
                FUNC_APPROVE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(owner),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getApproveInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_APPROVE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue()
        );
    }

    public Tuple1<Boolean> getApproveOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_APPROVE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
        );
    }

    public BigInteger balanceOf(String _owner) throws ContractException {
        final Function function = new Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public BigInteger balances(String param0) throws ContractException {
        final Function function = new Function(FUNC_BALANCES,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public BigInteger decimals() throws ContractException {
        final Function function = new Function(FUNC_DECIMALS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public String name() throws ContractException {
        final Function function = new Function(FUNC_NAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt pledge(String supplier, BigInteger counter) {
        final Function function = new Function(
                FUNC_PLEDGE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(supplier),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(counter)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] pledge(String supplier, BigInteger counter, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_PLEDGE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(supplier),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(counter)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForPledge(String supplier, BigInteger counter) {
        final Function function = new Function(
                FUNC_PLEDGE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(supplier),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(counter)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getPledgeInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_PLEDGE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue()
        );
    }

    public TransactionReceipt register(String user) {
        final Function function = new Function(
                FUNC_REGISTER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] register(String user, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REGISTER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRegister(String user) {
        final Function function = new Function(
                FUNC_REGISTER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getRegisterInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REGISTER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
        );
    }

    public TransactionReceipt reward(String supplier, BigInteger count) {
        final Function function = new Function(
                FUNC_REWARD,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(supplier),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(count)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] reward(String supplier, BigInteger count, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REWARD,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(supplier),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(count)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForReward(String supplier, BigInteger count) {
        final Function function = new Function(
                FUNC_REWARD,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(supplier),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(count)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getRewardInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REWARD,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue()
        );
    }

    public String symbol() throws ContractException {
        final Function function = new Function(FUNC_SYMBOL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public BigInteger totalSupply() throws ContractException {
        final Function function = new Function(FUNC_TOTALSUPPLY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt transfer(String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_to),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] transfer(String _to, BigInteger _value, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_to),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForTransfer(String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_to),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_value)),
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

    public Tuple1<Boolean> getTransferOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_TRANSFER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
        );
    }

    public TransactionReceipt transferFrom(String _from, String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_from),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(_to),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] transferFrom(String _from, String _to, BigInteger _value, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFERFROM,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_from),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(_to),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForTransferFrom(String _from, String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_from),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(_to),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple3<String, String, BigInteger> getTransferFromInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_TRANSFERFROM,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, String, BigInteger>(

                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                (BigInteger) results.get(2).getValue()
        );
    }

    public Tuple1<Boolean> getTransferFromOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_TRANSFERFROM,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
        );
    }

    public static ContractTokenService load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ContractTokenService(contractAddress, client, credential);
    }

    public static ContractTokenService deploy(Client client, CryptoKeyPair credential, int _initialAmount, String _tokenName, int _decimalUnits, String _tokenSymbol) throws ContractException {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_initialAmount),
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_tokenName),
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(_decimalUnits),
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_tokenSymbol)));
        return deploy(ContractTokenService.class, client, credential, getBinary(client.getCryptoSuite()), encodedConstructor);
    }

    public static class ApprovalEventResponse {
        public TransactionReceipt.Logs log;

        public String _owner;

        public String _spender;

        public BigInteger _value;
    }

    public static class TransferEventResponse {
        public TransactionReceipt.Logs log;

        public String _from;

        public String _to;

        public BigInteger _value;
    }
}