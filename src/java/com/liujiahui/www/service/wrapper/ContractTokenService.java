package com.liujiahui.www.service.wrapper;

import org.fisco.bcos.sdk.abi.FunctionEncoder;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.*;
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class ContractTokenService extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040523480156200001157600080fd5b5060405162000d4738038062000d478339810160408190526200003491620001ef565b3360009081526020818152604090912060c8908190556002558351620000619160049190860190620000a3565b506005805460ff191660ff8416179055805162000086906006906020840190620000a3565b5050600780546001600160a01b03191633179055506200026f9050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620000e657805160ff191683800117855562000116565b8280016001018555821562000116579182015b8281111562000116578251825591602001919060010190620000f9565b506200012492915062000128565b5090565b6200014591905b808211156200012457600081556001016200012f565b90565b600082601f83011262000159578081fd5b81516001600160401b038082111562000170578283fd5b6040516020601f8401601f191682018101838111838210171562000192578586fd5b80604052508194508382528681858801011115620001af57600080fd5b600092505b83831015620001d35785830181015182840182015291820191620001b4565b83831115620001e55760008185840101525b5050505092915050565b60008060006060848603121562000204578283fd5b83516001600160401b03808211156200021b578485fd5b620002298783880162000148565b94506020860151915060ff8216821462000241578384fd5b60408601519193508082111562000256578283fd5b50620002658682870162000148565b9150509250925092565b610ac8806200027f6000396000f3fe608060405234801561001057600080fd5b50600436106100f55760003560e01c80634420e486116100975780639137c1a7116100665780639137c1a7146101e957806395d89b41146101fc578063a9059cbb14610204578063dd62ed3e14610217576100f5565b80634420e4861461019d5780635c658165146101b057806370a08231146101c3578063743b3452146101d6576100f5565b806321670f22116100d357806321670f221461014d57806323b872dd1461016257806327e235e314610175578063313ce56714610188576100f5565b806306fdde03146100fa578063095ea7b31461011857806318160ddd14610138575b600080fd5b61010261022a565b60405161010f91906108d7565b60405180910390f35b61012b6101263660046108a2565b6102b8565b60405161010f91906108cc565b610140610361565b60405161010f9190610a63565b61016061015b3660046108a2565b610367565b005b61012b610170366004610862565b6103a0565b61014061018336600461080c565b6104eb565b6101906104fd565b60405161010f9190610a6c565b6101606101ab36600461080c565b610506565b6101406101be36600461082e565b61054c565b6101406101d136600461080c565b610569565b6101606101e43660046108a2565b6105b2565b6101606101f736600461080c565b6105f4565b610102610665565b61012b6102123660046108a2565b6106c0565b61014061022536600461082e565b61079b565b6004805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102b05780601f10610285576101008083540402835291602001916102b0565b820191906000526020600020905b81548152906001019060200180831161029357829003601f168201915b505050505081565b6003546000906001600160a01b031633146102ee5760405162461bcd60e51b81526004016102e5906109a2565b60405180910390fd5b6001600160a01b0380841660008181526001602090815260408083206007805487168552925291829020869055549051919216907f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9259061034f908690610a63565b60405180910390a35060015b92915050565b60025481565b6003546001600160a01b031633146103915760405162461bcd60e51b81526004016102e5906109a2565b61039b82826106c0565b505050565b6003546000906001600160a01b031633146103cd5760405162461bcd60e51b81526004016102e5906109a2565b6001600160a01b038085166000818152600160209081526040808320600754909516835293815283822054928252819052919091205483118015906104125750828110155b61042e5760405162461bcd60e51b81526004016102e5906109c4565b6001600160a01b0380851660009081526020819052604080822080548701905591871681522080548490039055600019811015610495576001600160a01b038086166000908152600160209081526040808320600754909416835292905220805484900390555b836001600160a01b0316856001600160a01b03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040516104d89190610a63565b60405180910390a3506001949350505050565b60006020819052908152604090205481565b60055460ff1681565b6003546001600160a01b031633146105305760405162461bcd60e51b81526004016102e5906109a2565b61053c816103e86102b8565b506105488160646106c0565b5050565b600160209081526000928352604080842090915290825290205481565b6003546000906001600160a01b031633146105965760405162461bcd60e51b81526004016102e5906109a2565b506001600160a01b031660009081526020819052604090205490565b6003546001600160a01b031633146105dc5760405162461bcd60e51b81526004016102e5906109a2565b60075461039b9083906001600160a01b0316836103a0565b6001600160a01b03811661061a5760405162461bcd60e51b81526004016102e59061092a565b6003546001600160a01b0316156106435760405162461bcd60e51b81526004016102e590610a21565b600380546001600160a01b0319166001600160a01b0392909216919091179055565b6006805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102b05780601f10610285576101008083540402835291602001916102b0565b6003546000906001600160a01b031633146106ed5760405162461bcd60e51b81526004016102e5906109a2565b6007546001600160a01b03166000908152602081905260409020548211156107275760405162461bcd60e51b81526004016102e590610953565b61072f6107c6565b600780546001600160a01b039081166000908152602081905260408082208054879003905586831680835291819020805487019055925492519092909116907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9061034f908690610a63565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b6002805460649081019091556007546001600160a01b0316600090815260208190526040902080549091019055565b80356001600160a01b038116811461035b57600080fd5b60006020828403121561081d578081fd5b61082783836107f5565b9392505050565b60008060408385031215610840578081fd5b61084a84846107f5565b915061085984602085016107f5565b90509250929050565b600080600060608486031215610876578081fd5b833561088181610a7a565b9250602084013561089181610a7a565b929592945050506040919091013590565b600080604083850312156108b4578182fd5b6108be84846107f5565b946020939093013593505050565b901515815260200190565b6000602080835283518082850152825b81811015610903578581018301518582016040015282016108e7565b818111156109145783604083870101525b50601f01601f1916929092016040019392505050565b6020808252600f908201526e496e76616c6964206164647265737360881b604082015260600190565b6020808252602f908201527f746f6b656e2062616c616e6365206973206c6f776572207468616e207468652060408201526e1d985b1d59481c995c5d595cdd1959608a1b606082015260800190565b602080825260089082015267139bc81c9a59da1d60c21b604082015260600190565b60208082526039908201527f746f6b656e2062616c616e6365206f7220616c6c6f77616e6365206973206c6f60408201527f776572207468616e20616d6f756e742072657175657374656400000000000000606082015260800190565b60208082526022908201527f4c6f67696320636f6e7472616374206164647265737320616c72656164792073604082015261195d60f21b606082015260800190565b90815260200190565b60ff91909116815260200190565b6001600160a01b0381168114610a8f57600080fd5b5056fea2646970667358221220761bf8152e3676f52e68395847239a73903daaa7ada5e7b7c706e4eaa1154ffd64736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040523480156200001157600080fd5b5060405162000d5238038062000d528339810160408190526200003491620001ef565b3360009081526020818152604090912060c8908190556002558351620000619160049190860190620000a3565b506005805460ff191660ff8416179055805162000086906006906020840190620000a3565b5050600780546001600160a01b03191633179055506200026f9050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620000e657805160ff191683800117855562000116565b8280016001018555821562000116579182015b8281111562000116578251825591602001919060010190620000f9565b506200012492915062000128565b5090565b6200014591905b808211156200012457600081556001016200012f565b90565b600082601f83011262000159578081fd5b81516001600160401b038082111562000170578283fd5b6040516020601f8401601f191682018101838111838210171562000192578586fd5b80604052508194508382528681858801011115620001af57600080fd5b600092505b83831015620001d35785830181015182840182015291820191620001b4565b83831115620001e55760008185840101525b5050505092915050565b60008060006060848603121562000204578283fd5b83516001600160401b03808211156200021b578485fd5b620002298783880162000148565b94506020860151915060ff8216821462000241578384fd5b60408601519193508082111562000256578283fd5b50620002658682870162000148565b9150509250925092565b610ad3806200027f6000396000f3fe608060405234801561001057600080fd5b50600436106100f55760003560e01c8063852d921311610097578063cc8be70e11610066578063cc8be70e146101de578063d4b01426146101f1578063e3f36d6314610204578063facc639614610217576100f5565b8063852d92131461019d57806394ed4dfd146101b0578063ad8a9731146101c3578063b11b6883146101d6576100f5565b806346b13615116100d357806346b136151461014d5780635bfa27961461016257806360e639f3146101775780636904e9651461018a576100f5565b80630256e278146100fa5780631e7d6248146101185780631f2d48601461012d575b600080fd5b61010261022a565b60405161010f9190610a6e565b60405180910390f35b61012b6101263660046108ad565b610230565b005b61014061013b3660046108ad565b610273565b60405161010f91906108d7565b610155610314565b60405161010f9190610a77565b61016a61031d565b60405161010f91906108e2565b61012b6101853660046108ad565b6103ab565b6101406101983660046108ad565b6103ee565b6101026101ab366004610839565b6104cb565b61012b6101be366004610817565b6104f6565b6101406101d136600461086d565b61053d565b61016a61068a565b6101026101ec366004610817565b6106e5565b6101026101ff366004610839565b61072f565b610102610212366004610817565b61074c565b61012b610225366004610817565b61075e565b60025481565b6003546001600160a01b0316331461026457604051636381e58960e11b815260040161025b90610935565b60405180910390fd5b61026e82826103ee565b505050565b6003546000906001600160a01b031633146102a157604051636381e58960e11b815260040161025b90610935565b6001600160a01b0380841660008181526001602090815260408083206007805487168552925291829020869055549051919216907fd1e45707b3f71c77903b61f04c900f772db264b9bf618f1cc3308fb516eb616990610302908690610a6e565b60405180910390a35060015b92915050565b60055460ff1681565b6006805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103a35780601f10610378576101008083540402835291602001916103a3565b820191906000526020600020905b81548152906001019060200180831161038657829003601f168201915b505050505081565b6003546001600160a01b031633146103d657604051636381e58960e11b815260040161025b90610935565b60075461026e9083906001600160a01b03168361053d565b6003546000906001600160a01b0316331461041c57604051636381e58960e11b815260040161025b90610935565b6007546001600160a01b031660009081526020819052604090205482111561045757604051636381e58960e11b815260040161025b90610980565b61045f6107d1565b600780546001600160a01b039081166000908152602081905260408082208054879003905586831680835291819020805487019055925492519092909116907f18f84334255a242551aa98c68047b5da8063eab9fbeaec1eddeea280044b9ff190610302908690610a6e565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b6003546001600160a01b0316331461052157604051636381e58960e11b815260040161025b90610935565b61052d816103e8610273565b506105398160646103ee565b5050565b6003546000906001600160a01b0316331461056b57604051636381e58960e11b815260040161025b90610935565b6001600160a01b038085166000818152600160209081526040808320600754909516835293815283822054928252819052919091205483118015906105b05750828110155b6105cd57604051636381e58960e11b815260040161025b906109cf565b6001600160a01b0380851660009081526020819052604080822080548701905591871681522080548490039055600019811015610634576001600160a01b038086166000908152600160209081526040808320600754909416835292905220805484900390555b836001600160a01b0316856001600160a01b03167f18f84334255a242551aa98c68047b5da8063eab9fbeaec1eddeea280044b9ff1856040516106779190610a6e565b60405180910390a3506001949350505050565b6004805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103a35780601f10610378576101008083540402835291602001916103a3565b6003546000906001600160a01b0316331461071357604051636381e58960e11b815260040161025b90610935565b506001600160a01b031660009081526020819052604090205490565b600160209081526000928352604080842090915290825290205481565b60006020819052908152604090205481565b6001600160a01b03811661078557604051636381e58960e11b815260040161025b90610957565b6003546001600160a01b0316156107af57604051636381e58960e11b815260040161025b90610a2c565b600380546001600160a01b0319166001600160a01b0392909216919091179055565b6002805460649081019091556007546001600160a01b0316600090815260208190526040902080549091019055565b80356001600160a01b038116811461030e57600080fd5b600060208284031215610828578081fd5b6108328383610800565b9392505050565b6000806040838503121561084b578081fd5b6108558484610800565b91506108648460208501610800565b90509250929050565b600080600060608486031215610881578081fd5b833561088c81610a85565b9250602084013561089c81610a85565b929592945050506040919091013590565b600080604083850312156108bf578182fd5b6108c98484610800565b946020939093013593505050565b901515815260200190565b6000602080835283518082850152825b8181101561090e578581018301518582016040015282016108f2565b8181111561091f5783604083870101525b50601f01601f1916929092016040019392505050565b602080825260089082015267139bc81c9a59da1d60c21b604082015260600190565b6020808252600f908201526e496e76616c6964206164647265737360881b604082015260600190565b6020808252602f908201527f746f6b656e2062616c616e6365206973206c6f776572207468616e207468652060408201526e1d985b1d59481c995c5d595cdd1959608a1b606082015260800190565b60208082526039908201527f746f6b656e2062616c616e6365206f7220616c6c6f77616e6365206973206c6f60408201527f776572207468616e20616d6f756e742072657175657374656400000000000000606082015260800190565b60208082526022908201527f4c6f67696320636f6e7472616374206164647265737320616c72656164792073604082015261195d60f21b606082015260800190565b90815260200190565b60ff91909116815260200190565b6001600160a01b0381168114610a9a57600080fd5b5056fea2646970667358221220a22032d74920fd083f4b3f27e48ad404654bad6e92fea611346f008e390a9d0964736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"string\",\"name\":\"_tokenName\",\"type\":\"string\"},{\"internalType\":\"uint8\",\"name\":\"_decimalUnits\",\"type\":\"uint8\"},{\"internalType\":\"string\",\"name\":\"_tokenSymbol\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_spender\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_spender\",\"type\":\"address\"}],\"name\":\"allowance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"remaining\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"allowed\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"balance\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"balances\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"decimals\",\"outputs\":[{\"internalType\":\"uint8\",\"name\":\"\",\"type\":\"uint8\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"supplier\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"counter\",\"type\":\"uint256\"}],\"name\":\"pledge\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"register\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"supplier\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"count\",\"type\":\"uint256\"}],\"name\":\"reward\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"storageAddress\",\"type\":\"address\"}],\"name\":\"setStorage\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

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

    public static final String FUNC_SETSTORAGE = "setStorage";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final Event APPROVAL_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;

    protected ContractTokenService(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static ContractTokenService load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ContractTokenService(contractAddress, client, credential);
    }

    public static ContractTokenService deploy(Client client, CryptoKeyPair credential, String _tokenName, BigInteger _decimalUnits, String _tokenSymbol) throws ContractException {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_tokenName),
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(_decimalUnits),
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_tokenSymbol)));
        return deploy(ContractTokenService.class, client, credential, getBinary(client.getCryptoSuite()), encodedConstructor);
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
        subscribeEvent(ABI, BINARY, topic0, fromBlock, toBlock, otherTopics, callback);
    }

    public void subscribeApprovalEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(APPROVAL_EVENT);
        subscribeEvent(ABI, BINARY, topic0, callback);
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
        subscribeEvent(ABI, BINARY, topic0, fromBlock, toBlock, otherTopics, callback);
    }

    public void subscribeTransferEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(TRANSFER_EVENT);
        subscribeEvent(ABI, BINARY, topic0, callback);
    }

    public BigInteger allowance(String _owner, String _spender) throws ContractException {
        final Function function = new Function(FUNC_ALLOWANCE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_owner),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(_spender)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public BigInteger allowed(String param0, String param1) throws ContractException {
        final Function function = new Function(FUNC_ALLOWED,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(param0),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
        );
    }

    public BigInteger balanceOf(String _owner) throws ContractException {
        final Function function = new Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public BigInteger balances(String param0) throws ContractException {
        final Function function = new Function(FUNC_BALANCES,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public BigInteger decimals() throws ContractException {
        final Function function = new Function(FUNC_DECIMALS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
                }));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public String name() throws ContractException {
        final Function function = new Function(FUNC_NAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue()
        );
    }

    public TransactionReceipt setStorage(String storageAddress) {
        final Function function = new Function(
                FUNC_SETSTORAGE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(storageAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] setStorage(String storageAddress, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETSTORAGE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(storageAddress)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetStorage(String storageAddress) {
        final Function function = new Function(
                FUNC_SETSTORAGE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(storageAddress)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getSetStorageInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETSTORAGE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
        );
    }

    public String symbol() throws ContractException {
        final Function function = new Function(FUNC_SYMBOL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public BigInteger totalSupply() throws ContractException {
        final Function function = new Function(FUNC_TOTALSUPPLY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
        );
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