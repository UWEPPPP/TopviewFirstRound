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
    public static final String[] BINARY_ARRAY = {"60806040523480156200001157600080fd5b506040516200104d3803806200104d8339810160408190526200003491620001ef565b3360009081526020818152604090912060c8908190556002558351620000619160069190860190620000a3565b506007805460ff191660ff8416179055805162000086906008906020840190620000a3565b5050600980546001600160a01b03191633179055506200026f9050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620000e657805160ff191683800117855562000116565b8280016001018555821562000116579182015b8281111562000116578251825591602001919060010190620000f9565b506200012492915062000128565b5090565b6200014591905b808211156200012457600081556001016200012f565b90565b600082601f83011262000159578081fd5b81516001600160401b038082111562000170578283fd5b6040516020601f8401601f191682018101838111838210171562000192578586fd5b80604052508194508382528681858801011115620001af57600080fd5b600092505b83831015620001d35785830181015182840182015291820191620001b4565b83831115620001e55760008185840101525b5050505092915050565b60008060006060848603121562000204578283fd5b83516001600160401b03808211156200021b578485fd5b620002298783880162000148565b94506020860151915060ff8216821462000241578384fd5b60408601519193508082111562000256578283fd5b50620002658682870162000148565b9150509250925092565b610dce806200027f6000396000f3fe608060405234801561001057600080fd5b50600436106100f55760003560e01c80634420e48611610097578063743b345211610066578063743b3452146101e957806395d89b41146101fc578063a9059cbb14610204578063dd62ed3e14610217576100f5565b80634420e4861461019d5780635c658165146101b05780635d479bb2146101c357806370a08231146101d6576100f5565b806321670f22116100d357806321670f221461014d57806323b872dd1461016257806327e235e314610175578063313ce56714610188576100f5565b806306fdde03146100fa578063095ea7b31461011857806318160ddd14610138575b600080fd5b61010261022a565b60405161010f9190610bf5565b60405180910390f35b61012b610126366004610b34565b6102b8565b60405161010f9190610bea565b610140610376565b60405161010f9190610d39565b61016061015b366004610b34565b61037c565b005b61012b610170366004610a46565b6103ca565b6101406101833660046109f0565b61052a565b61019061053c565b60405161010f9190610d42565b6101606101ab3660046109f0565b610545565b6101406101be366004610a12565b6105a0565b6101606101d1366004610a86565b6105bd565b6101406101e43660046109f0565b6106e4565b6101606101f7366004610b34565b610742565b610102610799565b61012b610212366004610b34565b6107f4565b610140610225366004610a12565b6108e4565b6006805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102b05780601f10610285576101008083540402835291602001916102b0565b820191906000526020600020905b81548152906001019060200180831161029357829003601f168201915b505050505081565b6003546000906001600160a01b03163314806102de57506005546001600160a01b031633145b6103035760405162461bcd60e51b81526004016102fa90610c98565b60405180910390fd5b6001600160a01b0380841660008181526001602090815260408083206009805487168552925291829020869055549051919216907f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92590610364908690610d39565b60405180910390a35060015b92915050565b60025481565b6003546001600160a01b031633148061039f57506005546001600160a01b031633145b6103bb5760405162461bcd60e51b81526004016102fa90610c98565b6103c582826107f4565b505050565b6003546000906001600160a01b03163314806103f057506005546001600160a01b031633145b61040c5760405162461bcd60e51b81526004016102fa90610c98565b6001600160a01b038085166000818152600160209081526040808320600954909516835293815283822054928252819052919091205483118015906104515750828110155b61046d5760405162461bcd60e51b81526004016102fa90610cba565b6001600160a01b03808516600090815260208190526040808220805487019055918716815220805484900390556000198110156104d4576001600160a01b038086166000908152600160209081526040808320600954909416835292905220805484900390555b836001600160a01b0316856001600160a01b03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040516105179190610d39565b60405180910390a3506001949350505050565b60006020819052908152604090205481565b60075460ff1681565b6003546001600160a01b031633148061056857506005546001600160a01b031633145b6105845760405162461bcd60e51b81526004016102fa90610c98565b610590816103e86102b8565b5061059c8160646107f4565b5050565b600160209081526000928352604080842090915290825290205481565b6001600160a01b0382166105e35760405162461bcd60e51b81526004016102fa90610c77565b6003546001600160a01b031661062757805161060690600490602084019061093e565b50600380546001600160a01b0319166001600160a01b03841617905561059c565b60046040516020016106399190610b7a565b60405160208183030381529060405280519060200120816040516020016106609190610b5e565b60405160208183030381529060405280519060200120146106935760405162461bcd60e51b81526004016102fa90610d17565b6005546001600160a01b03166106c357600580546001600160a01b0319166001600160a01b03841617905561059c565b600380546001600160a01b0384166001600160a01b03199091161790555050565b6003546000906001600160a01b031633148061070a57506005546001600160a01b031633145b6107265760405162461bcd60e51b81526004016102fa90610c98565b506001600160a01b031660009081526020819052604090205490565b6003546001600160a01b031633148061076557506005546001600160a01b031633145b6107815760405162461bcd60e51b81526004016102fa90610c98565b6009546103c59083906001600160a01b0316836103ca565b6008805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102b05780601f10610285576101008083540402835291602001916102b0565b6003546000906001600160a01b031633148061081a57506005546001600160a01b031633145b6108365760405162461bcd60e51b81526004016102fa90610c98565b6009546001600160a01b03166000908152602081905260409020548211156108705760405162461bcd60e51b81526004016102fa90610c28565b61087861090f565b600980546001600160a01b039081166000908152602081905260408082208054879003905586831680835291819020805487019055925492519092909116907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef90610364908690610d39565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b6002805460649081019091556009546001600160a01b0316600090815260208190526040902080549091019055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061097f57805160ff19168380011785556109ac565b828001600101855582156109ac579182015b828111156109ac578251825591602001919060010190610991565b506109b89291506109bc565b5090565b6109d691905b808211156109b857600081556001016109c2565b90565b80356001600160a01b038116811461037057600080fd5b600060208284031215610a01578081fd5b610a0b83836109d9565b9392505050565b60008060408385031215610a24578081fd5b610a2e84846109d9565b9150610a3d84602085016109d9565b90509250929050565b600080600060608486031215610a5a578081fd5b8335610a6581610d80565b92506020840135610a7581610d80565b929592945050506040919091013590565b60008060408385031215610a98578182fd5b8235610aa381610d80565b915060208381013567ffffffffffffffff80821115610ac0578384fd5b81860187601f820112610ad1578485fd5b8035925081831115610ae1578485fd5b604051601f8401601f1916810185018381118282101715610b00578687fd5b6040528381528184018501891015610b16578586fd5b83858301868301378585858301015280955050505050509250929050565b60008060408385031215610b46578182fd5b610b5084846109d9565b946020939093013593505050565b60008251610b70818460208701610d50565b9190910192915050565b6000808354600180821660008114610b995760018114610bb057610bdf565b60ff198316865260028304607f1686019350610bdf565b600283048786526020808720875b83811015610bd75781548a820152908501908201610bbe565b505050860193505b509195945050505050565b901515815260200190565b6000602082528251806020840152610c14816040850160208701610d50565b601f01601f19169190910160400192915050565b6020808252602f908201527f746f6b656e2062616c616e6365206973206c6f776572207468616e207468652060408201526e1d985b1d59481c995c5d595cdd1959608a1b606082015260800190565b602080825260079082015266125b9d985b1a5960ca1b604082015260600190565b602080825260089082015267139bc81c9a59da1d60c21b604082015260600190565b60208082526039908201527f746f6b656e2062616c616e6365206f7220616c6c6f77616e6365206973206c6f60408201527f776572207468616e20616d6f756e742072657175657374656400000000000000606082015260800190565b602080825260089082015267139bc8149a59da1d60c21b604082015260600190565b90815260200190565b60ff91909116815260200190565b60005b83811015610d6b578181015183820152602001610d53565b83811115610d7a576000848401525b50505050565b", "6001600160a01b0381168114610d9557600080fd5b5056fea2646970667358221220081851158fe78308dd2b1a83e1f8e3daa776fb8e856db14511e5cec978eb874c64736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040523480156200001157600080fd5b5060405162001058380380620010588339810160408190526200003491620001ef565b3360009081526020818152604090912060c8908190556002558351620000619160069190860190620000a3565b506007805460ff191660ff8416179055805162000086906008906020840190620000a3565b5050600980546001600160a01b03191633179055506200026f9050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620000e657805160ff191683800117855562000116565b8280016001018555821562000116579182015b8281111562000116578251825591602001919060010190620000f9565b506200012492915062000128565b5090565b6200014591905b808211156200012457600081556001016200012f565b90565b600082601f83011262000159578081fd5b81516001600160401b038082111562000170578283fd5b6040516020601f8401601f191682018101838111838210171562000192578586fd5b80604052508194508382528681858801011115620001af57600080fd5b600092505b83831015620001d35785830181015182840182015291820191620001b4565b83831115620001e55760008185840101525b5050505092915050565b60008060006060848603121562000204578283fd5b83516001600160401b03808211156200021b578485fd5b620002298783880162000148565b94506020860151915060ff8216821462000241578384fd5b60408601519193508082111562000256578283fd5b50620002658682870162000148565b9150509250925092565b610dd9806200027f6000396000f3fe608060405234801561001057600080fd5b50600436106100f55760003560e01c8063852d921311610097578063ca38d32611610066578063ca38d326146101de578063cc8be70e146101f1578063d4b0142614610204578063e3f36d6314610217576100f5565b8063852d92131461019d57806394ed4dfd146101b0578063ad8a9731146101c3578063b11b6883146101d6576100f5565b806346b13615116100d357806346b136151461014d5780635bfa27961461016257806360e639f3146101775780636904e9651461018a576100f5565b80630256e278146100fa5780631e7d6248146101185780631f2d48601461012d575b600080fd5b61010261022a565b60405161010f9190610d44565b60405180910390f35b61012b610126366004610b3f565b610230565b005b61014061013b366004610b3f565b610288565b60405161010f9190610bf5565b61015561033e565b60405161010f9190610d4d565b61016a610347565b60405161010f9190610c00565b61012b610185366004610b3f565b6103d5565b610140610198366004610b3f565b61042d565b6101026101ab366004610a1d565b61051f565b61012b6101be3660046109fb565b61054a565b6101406101d1366004610a51565b6105a6565b61016a610708565b61012b6101ec366004610a91565b610763565b6101026101ff3660046109fb565b61088c565b610102610212366004610a1d565b6108eb565b6101026102253660046109fb565b610908565b60025481565b6003546001600160a01b031633148061025357506005546001600160a01b031633145b61027957604051636381e58960e11b815260040161027090610c33565b60405180910390fd5b610283828261042d565b505050565b6003546000906001600160a01b03163314806102ae57506005546001600160a01b031633145b6102cb57604051636381e58960e11b815260040161027090610c33565b6001600160a01b0380841660008181526001602090815260408083206009805487168552925291829020869055549051919216907fd1e45707b3f71c77903b61f04c900f772db264b9bf618f1cc3308fb516eb61699061032c908690610d44565b60405180910390a35060015b92915050565b60075460ff1681565b6008805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103cd5780601f106103a2576101008083540402835291602001916103cd565b820191906000526020600020905b8154815290600101906020018083116103b057829003601f168201915b505050505081565b6003546001600160a01b03163314806103f857506005546001600160a01b031633145b61041557604051636381e58960e11b815260040161027090610c33565b6009546102839083906001600160a01b0316836105a6565b6003546000906001600160a01b031633148061045357506005546001600160a01b031633145b61047057604051636381e58960e11b815260040161027090610c33565b6009546001600160a01b03166000908152602081905260409020548211156104ab57604051636381e58960e11b815260040161027090610c55565b6104b361091a565b600980546001600160a01b039081166000908152602081905260408082208054879003905586831680835291819020805487019055925492519092909116907f18f84334255a242551aa98c68047b5da8063eab9fbeaec1eddeea280044b9ff19061032c908690610d44565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b6003546001600160a01b031633148061056d57506005546001600160a01b031633145b61058a57604051636381e58960e11b815260040161027090610c33565b610596816103e8610288565b506105a281606461042d565b5050565b6003546000906001600160a01b03163314806105cc57506005546001600160a01b031633145b6105e957604051636381e58960e11b815260040161027090610c33565b6001600160a01b0380851660008181526001602090815260408083206009549095168352938152838220549282528190529190912054831180159061062e5750828110155b61064b57604051636381e58960e11b815260040161027090610cc6565b6001600160a01b03808516600090815260208190526040808220805487019055918716815220805484900390556000198110156106b2576001600160a01b038086166000908152600160209081526040808320600954909416835292905220805484900390555b836001600160a01b0316856001600160a01b03167f18f84334255a242551aa98c68047b5da8063eab9fbeaec1eddeea280044b9ff1856040516106f59190610d44565b60405180910390a3506001949350505050565b6006805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103cd5780601f106103a2576101008083540402835291602001916103cd565b6001600160a01b03821661078a57604051636381e58960e11b815260040161027090610d23565b6003546001600160a01b03166107ce5780516107ad906004906020840190610949565b50600380546001600160a01b0319166001600160a01b0384161790556105a2565b60046040516020016107e09190610b85565b60405160208183030381529060405280519060200120816040516020016108079190610b69565b604051602081830303815290604052805190602001201461083b57604051636381e58960e11b815260040161027090610ca4565b6005546001600160a01b031661086b57600580546001600160a01b0319166001600160a01b0384161790556105a2565b600380546001600160a01b0384166001600160a01b03199091161790555050565b6003546000906001600160a01b03163314806108b257506005546001600160a01b031633145b6108cf57604051636381e58960e11b815260040161027090610c33565b506001600160a01b031660009081526020819052604090205490565b600160209081526000928352604080842090915290825290205481565b60006020819052908152604090205481565b6002805460649081019091556009546001600160a01b0316600090815260208190526040902080549091019055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061098a57805160ff19168380011785556109b7565b828001600101855582156109b7579182015b828111156109b757825182559160200191906001019061099c565b506109c39291506109c7565b5090565b6109e191905b808211156109c357600081556001016109cd565b90565b80356001600160a01b038116811461033857600080fd5b600060208284031215610a0c578081fd5b610a1683836109e4565b9392505050565b60008060408385031215610a2f578081fd5b610a3984846109e4565b9150610a4884602085016109e4565b90509250929050565b600080600060608486031215610a65578081fd5b8335610a7081610d8b565b92506020840135610a8081610d8b565b929592945050506040919091013590565b60008060408385031215610aa3578182fd5b8235610aae81610d8b565b915060208381013567ffffffffffffffff80821115610acb578384fd5b81860187601f820112610adc578485fd5b8035925081831115610aec578485fd5b604051601f8401601f1916810185018381118282101715610b0b578687fd5b6040528381528184018501891015610b21578586fd5b83858301868301378585858301015280955050505050509250929050565b60008060408385031215610b51578182fd5b610b5b84846109e4565b946020939093013593505050565b60008251610b7b818460208701610d5b565b9190910192915050565b6000808354600180821660008114610ba45760018114610bbb57610bea565b60ff198316865260028304607f1686019350610bea565b600283048786526020808720875b83811015610be25781548a820152908501908201610bc9565b505050860193505b509195945050505050565b901515815260200190565b6000602082528251806020840152610c1f816040850160208701610d5b565b601f01601f19169190910160400192915050565b602080825260089082015267139bc81c9a59da1d60c21b604082015260600190565b6020808252602f908201527f746f6b656e2062616c616e6365206973206c6f776572207468616e207468652060408201526e1d985b1d59481c995c5d595cdd1959608a1b606082015260800190565b602080825260089082015267139bc8149a59da1d60c21b604082015260600190565b60208082526039908201527f746f6b656e2062616c616e6365206f7220616c6c6f77616e6365206973206c6f60408201527f776572207468616e20616d6f756e742072657175657374656400000000000000606082015260800190565b602080825260079082015266125b9d985b1a5960ca1b604082015260600190565b90815260200190565b60ff91909116815260200190565b60005b83811015610d76578181015183820152602001610d5e565b83811115610d85576000", "848401525b50505050565b6001600160a01b0381168114610da057600080fd5b5056fea26469706673582212202e75c3e7ff1089a26db2e1d5771bed40e835e026298477709485f99c000df41464736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"string\",\"name\":\"_tokenName\",\"type\":\"string\"},{\"internalType\":\"uint8\",\"name\":\"_decimalUnits\",\"type\":\"uint8\"},{\"internalType\":\"string\",\"name\":\"_tokenSymbol\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_spender\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_spender\",\"type\":\"address\"}],\"name\":\"allowance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"remaining\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"allowed\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"balance\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"balances\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"decimals\",\"outputs\":[{\"internalType\":\"uint8\",\"name\":\"\",\"type\":\"uint8\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"supplier\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"counter\",\"type\":\"uint256\"}],\"name\":\"pledge\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"register\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"supplier\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"count\",\"type\":\"uint256\"}],\"name\":\"reward\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"logicAddress\",\"type\":\"address\"},{\"internalType\":\"string\",\"name\":\"password_\",\"type\":\"string\"}],\"name\":\"setLogic\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

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

    public static final String FUNC_SETLOGIC = "setLogic";

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

    public TransactionReceipt setLogic(String logicAddress, String password_) {
        final Function function = new Function(
                FUNC_SETLOGIC,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(logicAddress),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(password_)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] setLogic(String logicAddress, String password_, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETLOGIC,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(logicAddress),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(password_)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetLogic(String logicAddress, String password_) {
        final Function function = new Function(
                FUNC_SETLOGIC,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(logicAddress),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(password_)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, String> getSetLogicInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETLOGIC,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Utf8String>() {
                }));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(),
                (String) results.get(1).getValue()
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