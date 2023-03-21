package com.liujiahui.www.solidity;
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
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.abi.datatypes.DynamicStruct;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
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
public class ItemTrade extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040526003805460ff1916905534801561001a57600080fd5b506040516111af3803806111af8339810160408190526100399161005e565b600080546001600160a01b0319166001600160a01b039290921691909117905561008c565b60006020828403121561006f578081fd5b81516001600160a01b0381168114610085578182fd5b9392505050565b6111148061009b6000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806312065fe014610067578063582fd617146100855780639f37092a1461009a578063a3e54b3e146100ad578063d650d123146100c0578063f71ac7b6146100e1575b600080fd5b61006f6100f6565b60405161007c91906110a5565b60405180910390f35b610098610093366004610d6b565b61017d565b005b6100986100a8366004610d35565b6101e4565b61006f6100bb366004610d83565b61059d565b6100d36100ce366004610d6b565b610893565b60405161007c929190610f83565b6100e96109eb565b60405161007c9190610ec0565b6000805460405163f8b2cb4f60e01b81526001600160a01b039091169063f8b2cb4f90610127903390600401610e93565b60206040518083038186803b15801561013f57600080fd5b505afa158015610153573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101779190610ded565b90505b90565b60005460405163b7785afb60e01b81526001600160a01b039091169063b7785afb906101af9033908590600401610ea7565b600060405180830381600087803b1580156101c957600080fd5b505af11580156101dd573d6000803e3d6000fd5b5050505050565b60035460ff16156102105760405162461bcd60e51b81526004016102079061102b565b60405180910390fd5b6003805460ff19166001179055610225610bd4565b6001600160a01b038316600090815260016020526040902080548390811061024957fe5b90600052602060002090600602016040518060e001604052908160008201548152602001600182018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103055780601f106102da57610100808354040283529160200191610305565b820191906000526020600020905b8154815290600101906020018083116102e857829003601f168201915b505050918352505060028281015460208084019190915260038401805460408051601f600019610100600186161502019093169590950491820184900484028501840181528185529094019390918301828280156103a45780601f10610379576101008083540402835291602001916103a4565b820191906000526020600020905b81548152906001019060200180831161038757829003601f168201915b5050509183525050600482015460ff81161515602083015261010090046001600160a01b031660408201526005909101546060909101526080810151909150156104005760405162461bcd60e51b815260040161020790611053565b6104086100f6565b8160400151111561042b5760405162461bcd60e51b815260040161020790610fd3565b6001600160a01b038316600090815260016020819052604090912080548490811061045257fe5b60009182526020822060046006909202018101805493151560ff1990941693909317909255546040808401519051600162fa96b760e01b031981526001600160a01b039092169263ff056949926104ad923392909101610ea7565b600060405180830381600087803b1580156104c757600080fd5b505af11580156104db573d6000803e3d6000fd5b50506000546040808501519051635b86f59960e01b81526001600160a01b039092169350635b86f599925061051591879190600401610ea7565b600060405180830381600087803b15801561052f57600080fd5b505af1158015610543573d6000803e3d6000fd5b50505050826001600160a01b03167fb85097e0a99c7adb5b4581fb7c049733522cbccc6a256ff60d83fcb9b94b3655338360c00151604051610586929190610ea7565b60405180910390a250506003805460ff1916905550565b60008054604051633cb715a960e21b815282916001600160a01b03169063f2dc56a4906105ce903390600401610e93565b60206040518083038186803b1580156105e657600080fd5b505afa1580156105fa573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061061e9190610ded565b9050806001148061062f5750806002145b61064b5760405162461bcd60e51b815260040161020790611079565b8060011461066b5760405162461bcd60e51b815260040161020790610ffd565b33600081815260016020908152604080832054905190936106949285928b928b928b9201610e31565b6040516020818303038152906040528051906020012090506106b4610bd4565b506040805160e08101825283815260208082018a81528284018a9052606083018990526000608084018190523360a0850181905260c0850187905281526001808452948120805480870182559082529083902084516006909202019081559051805193948594929361072b93918501920190610c1f565b506040820151600282015560608201518051610751916003840191602090910190610c1f565b50608082015160048201805460a08501516001600160a01b031661010002610100600160a81b031993151560ff19909216919091179290921691909117905560c09091015160059091015560008281526002602090815260409091208251815581830151805184936107ca926001850192910190610c1f565b5060408201516002820155606082015180516107f0916003840191602090910190610c1f565b50608082015160048201805460a08501516001600160a01b031661010002610100600160a81b031993151560ff19909216919091179290921691909117905560c09091015160059091015560405133907f2db22df1631edf2d77f9b36c2a4277805c00cc152fddb472fd9d0d23c9dd39509061086f908b908b90610fb1565b60405180910390a25050336000908152600160205260409020549695505050505050565b606080600260008481526020019081526020016000206001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156109405780601f1061091557610100808354040283529160200191610940565b820191906000526020600020905b81548152906001019060200180831161092357829003601f168201915b505050600086815260026020818152604092839020600301805484516001821615610100026000190190911693909304601f8101839004830284018301909452838352959750909493509091508301828280156109de5780601f106109b3576101008083540402835291602001916109de565b820191906000526020600020905b8154815290600101906020018083116109c157829003601f168201915b5093945050505050915091565b336000908152600160209081526040808320805482518185028101850190935280835260609492939192909184015b82821015610bcb57838290600052602060002090600602016040518060e001604052908160008201548152602001600182018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610ae05780601f10610ab557610100808354040283529160200191610ae0565b820191906000526020600020905b815481529060010190602001808311610ac357829003601f168201915b505050918352505060028281015460208084019190915260038401805460408051601f60001961010060018616150201909316959095049182018490048402850184018152818552909401939091830182828015610b7f5780601f10610b5457610100808354040283529160200191610b7f565b820191906000526020600020905b815481529060010190602001808311610b6257829003601f168201915b5050509183525050600482015460ff811615156020808401919091526101009091046001600160a01b031660408301526005909201546060909101529082526001929092019101610a1a565b50505050905090565b6040518060e001604052806000815260200160608152602001600081526020016060815260200160001515815260200160006001600160a01b03168152602001600080191681525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610c6057805160ff1916838001178555610c8d565b82800160010185558215610c8d579182015b82811115610c8d578251825591602001919060010190610c72565b50610c99929150610c9d565b5090565b61017a91905b80821115610c995760008155600101610ca3565b600082601f830112610cc7578081fd5b813567ffffffffffffffff80821115610cde578283fd5b604051601f8301601f191681016020018281118282101715610cfe578485fd5b604052828152925082848301602001861015610d1957600080fd5b8260208601602083013760006020848301015250505092915050565b60008060408385031215610d47578182fd5b82356001600160a01b0381168114610d5d578283fd5b946020939093013593505050565b600060208284031215610d7c578081fd5b5035919050565b600080600060608486031215610d97578081fd5b833567ffffffffffffffff80821115610dae578283fd5b610dba87838801610cb7565b9450602086013593506040860135915080821115610dd6578283fd5b50610de386828701610cb7565b9150509250925092565b600060208284031215610dfe578081fd5b5051919050565b60008151808452610e1d8160208601602086016110ae565b601f01601f19169290920160200192915050565b60008682528551610e49816020850160208a016110ae565b80830186602082015285519150610e678260408301602089016110ae565b60609490941b6bffffffffffffffffffffffff1916604094909101938401525050605401949350505050565b6001600160a01b0391909116815260200190565b6001600160a01b03929092168252602082015260400190565b60208082528251828201819052600091906040908185019080840286018301878501865b83811015610f7557603f19898403018552815160e08151855288820151818a870152610f1282870182610e05565b898401518a8801526060925082840151915086810383880152610f358183610e05565b60808581015115159089015260a0808601516001600160a01b03169089015260c09485015194909701939093525050","509386019390860190600101610ee4565b509098975050505050505050565b600060408252610f966040830185610e05565b8281036020840152610fa88185610e05565b95945050505050565b600060408252610fc46040830185610e05565b90508260208301529392505050565b60208082526010908201526f4e6f7420656e6f756768206d6f6e657960801b604082015260600190565b6020808252601490820152732cb7ba9020b932902737ba1021b7b739bab6b2b960611b604082015260600190565b6020808252600e908201526d14995b9b9d1c985b9d0810d85b1b60921b604082015260600190565b6020808252600c908201526b125d195b481a5cc81cdbdb1960a21b604082015260600190565b6020808252601290820152712cb7ba902732bb32b9102932b3b4b9ba32b960711b604082015260600190565b90815260200190565b60005b838110156110c95781810151838201526020016110b1565b838111156110d8576000848401525b5050505056fea2646970667358221220df24e1113dfba3448f66e39f2e9a7324478a1c52079c6de05fba3edfd277c5c764736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040526003805460ff1916905534801561001a57600080fd5b506040516111b13803806111b18339810160408190526100399161005e565b600080546001600160a01b0319166001600160a01b039290921691909117905561008c565b60006020828403121561006f578081fd5b81516001600160a01b0381168114610085578182fd5b9392505050565b6111168061009b6000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c8063036b4e4114610067578063078806f31461009157806314e6bf0d146100b1578063490d5467146100c6578063c0f25f43146100db578063eda1cd8a146100e3575b600080fd5b61007a610075366004610d6d565b6100f6565b604051610088929190610f85565b60405180910390f35b6100a461009f366004610d85565b61024e565b60405161008891906110a7565b6100c46100bf366004610d6d565b61054f565b005b6100ce6105b6565b6040516100889190610ec2565b6100a46107a0565b6100c46100f1366004610d37565b610826565b606080600260008481526020019081526020016000206001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156101a35780601f10610178576101008083540402835291602001916101a3565b820191906000526020600020905b81548152906001019060200180831161018657829003601f168201915b505050600086815260026020818152604092839020600301805484516001821615610100026000190190911693909304601f8101839004830284018301909452838352959750909493509091508301828280156102415780601f1061021657610100808354040283529160200191610241565b820191906000526020600020905b81548152906001019060200180831161022457829003601f168201915b5093945050505050915091565b6000805460405163348de0b160e11b815282916001600160a01b03169063691bc1629061027f903390600401610e95565b60206040518083038186803b15801561029757600080fd5b505afa1580156102ab573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906102cf9190610def565b905080600114806102e05750806002145b61030657604051636381e58960e11b81526004016102fd9061107b565b60405180910390fd5b8060011461032757604051636381e58960e11b81526004016102fd90610fd5565b33600081815260016020908152604080832054905190936103509285928b928b928b9201610e33565b604051602081830303815290604052805190602001209050610370610bd6565b506040805160e08101825283815260208082018a81528284018a9052606083018990526000608084018190523360a0850181905260c085018790528152600180845294812080548087018255908252908390208451600690920201908155905180519394859492936103e793918501920190610c21565b50604082015160028201556060820151805161040d916003840191602090910190610c21565b50608082015160048201805460a08501516001600160a01b031661010002610100600160a81b031993151560ff19909216919091179290921691909117905560c0909101516005909101556000828152600260209081526040909120825181558183015180518493610486926001850192910190610c21565b5060408201516002820155606082015180516104ac916003840191602090910190610c21565b50608082015160048201805460a08501516001600160a01b031661010002610100600160a81b031993151560ff19909216919091179290921691909117905560c09091015160059091015560405133907f80e863492b63f6a162c0f7ef99ef9f59519410da2dc51148db147b762173afeb9061052b908b908b90610fb3565b60405180910390a25050336000908152600160205260409020549695505050505050565b6000546040516311da8d2b60e31b81526001600160a01b0390911690638ed46958906105819033908590600401610ea9565b600060405180830381600087803b15801561059b57600080fd5b505af11580156105af573d6000803e3d6000fd5b5050505050565b336000908152600160209081526040808320805482518185028101850190935280835260609492939192909184015b8282101561079657838290600052602060002090600602016040518060e001604052908160008201548152602001600182018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106ab5780601f10610680576101008083540402835291602001916106ab565b820191906000526020600020905b81548152906001019060200180831161068e57829003601f168201915b505050918352505060028281015460208084019190915260038401805460408051601f6000196101006001861615020190931695909504918201849004840285018401815281855290940193909183018282801561074a5780601f1061071f5761010080835404028352916020019161074a565b820191906000526020600020905b81548152906001019060200180831161072d57829003601f168201915b5050509183525050600482015460ff811615156020808401919091526101009091046001600160a01b0316604083015260059092015460609091015290825260019290920191016105e5565b5050505090505b90565b60008054604051630fc6ebe160e21b81526001600160a01b0390911690633f1baf84906107d1903390600401610e95565b60206040518083038186803b1580156107e957600080fd5b505afa1580156107fd573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906108219190610def565b905090565b60035460ff161561084a57604051636381e58960e11b81526004016102fd90611053565b6003805460ff1916600117905561085f610bd6565b6001600160a01b038316600090815260016020526040902080548390811061088357fe5b90600052602060002090600602016040518060e001604052908160008201548152602001600182018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561093f5780601f106109145761010080835404028352916020019161093f565b820191906000526020600020905b81548152906001019060200180831161092257829003601f168201915b505050918352505060028281015460208084019190915260038401805460408051601f600019610100600186161502019093169590950491820184900484028501840181528185529094019390918301828280156109de5780601f106109b3576101008083540402835291602001916109de565b820191906000526020600020905b8154815290600101906020018083116109c157829003601f168201915b5050509183525050600482015460ff81161515602083015261010090046001600160a01b03166040820152600590910154606090910152608081015190915015610a3b57604051636381e58960e11b81526004016102fd9061102d565b610a436107a0565b81604001511115610a6757604051636381e58960e11b81526004016102fd90611003565b6001600160a01b0383166000908152600160208190526040909120805484908110610a8e57fe5b60009182526020822060046006909202018101805493151560ff19909416939093179092555460408084015190516311185c2760e01b81526001600160a01b03909216926311185c2792610ae6923392909101610ea9565b600060405180830381600087803b158015610b0057600080fd5b505af1158015610b14573d6000803e3d6000fd5b50506000546040808501519051630ec12e8560e21b81526001600160a01b039092169350633b04ba149250610b4e91879190600401610ea9565b600060405180830381600087803b158015610b6857600080fd5b505af1158015610b7c573d6000803e3d6000fd5b50505050826001600160a01b03167f57d08a4fd3fc53e9e9ebd1eb805cee904d0715a7cee14e0c9448435632d11be2338360c00151604051610bbf929190610ea9565b60405180910390a250506003805460ff1916905550565b6040518060e001604052806000815260200160608152602001600081526020016060815260200160001515815260200160006001600160a01b03168152602001600080191681525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610c6257805160ff1916838001178555610c8f565b82800160010185558215610c8f579182015b82811115610c8f578251825591602001919060010190610c74565b50610c9b929150610c9f565b5090565b61079d91905b80821115610c9b5760008155600101610ca5565b600082601f830112610cc9578081fd5b813567ffffffffffffffff80821115610ce0578283fd5b604051601f8301601f191681016020018281118282101715610d00578485fd5b604052828152925082848301602001861015610d1b57600080fd5b8260208601602083013760006020848301015250505092915050565b60008060408385031215610d49578182fd5b82356001600160a01b0381168114610d5f578283fd5b946020939093013593505050565b600060208284031215610d7e578081fd5b5035919050565b600080600060608486031215610d99578081fd5b833567ffffffffffffffff80821115610db0578283fd5b610dbc87838801610cb9565b9450602086013593506040860135915080821115610dd8578283fd5b50610de586828701610cb9565b9150509250925092565b600060208284031215610e00578081fd5b5051919050565b60008151808452610e1f8160208601602086016110b0565b601f01601f19169290920160200192915050565b60008682528551610e4b816020850160208a016110b0565b80830186602082015285519150610e698260408301602089016110b0565b60609490941b6bffffffffffffffffffffffff1916604094909101938401525050605401949350505050565b6001600160a01b0391909116815260200190565b6001600160a01b03929092168252602082015260400190565b60208082528251828201819052600091906040908185019080840286018301878501865b83811015610f7757603f19898403018552815160e08151855288820151818a870152610f1482870182610e07565b898401518a8801526060925082840151915086810383880152610f378183610e07565b60808581015115159089015260a0808601516001600160a01b03169089015260c0948501519490970193909352","5050509386019390860190600101610ee6565b509098975050505050505050565b600060408252610f986040830185610e07565b8281036020840152610faa8185610e07565b95945050505050565b600060408252610fc66040830185610e07565b90508260208301529392505050565b6020808252601490820152732cb7ba9020b932902737ba1021b7b739bab6b2b960611b604082015260600190565b60208082526010908201526f4e6f7420656e6f756768206d6f6e657960801b604082015260600190565b6020808252600c908201526b125d195b481a5cc81cdbdb1960a21b604082015260600190565b6020808252600e908201526d14995b9b9d1c985b9d0810d85b1b60921b604082015260600190565b6020808252601290820152712cb7ba902732bb32b9102932b3b4b9ba32b960711b604082015260600190565b90815260200190565b60005b838110156110cb5781810151838201526020016110b3565b838111156110da576000848401525b5050505056fea26469706673582212204c874f972e1c71e056e8242750bd9260905a9a50bd57ca674fdd9d924f62eb7c64736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"assetAddress\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"seller\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"buyer\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"ItemSold\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"seller\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"price\",\"type\":\"uint256\"}],\"name\":\"NewItemAdd\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"price\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"description\",\"type\":\"string\"}],\"name\":\"addItem\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"seller\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"buyItem\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getBalance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"getRealItem\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"description\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getSoldItem\",\"outputs\":[{\"components\":[{\"internalType\":\"uint256\",\"name\":\"id\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"price\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"description\",\"type\":\"string\"},{\"internalType\":\"bool\",\"name\":\"isSold\",\"type\":\"bool\"},{\"internalType\":\"address\",\"name\":\"seller\",\"type\":\"address\"},{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"internalType\":\"struct ItemTrade.Item[]\",\"name\":\"itemSold\",\"type\":\"tuple[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"choice\",\"type\":\"uint256\"}],\"name\":\"registerAsset\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_ADDITEM = "addItem";

    public static final String FUNC_BUYITEM = "buyItem";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_GETREALITEM = "getRealItem";

    public static final String FUNC_GETSOLDITEM = "getSoldItem";

    public static final String FUNC_REGISTERASSET = "registerAsset";

    public static final Event ITEMSOLD_EVENT = new Event("ItemSold",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event NEWITEMADD_EVENT = new Event("NewItemAdd",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    protected ItemTrade(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public List<ItemSoldEventResponse> getItemSoldEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ITEMSOLD_EVENT, transactionReceipt);
        ArrayList<ItemSoldEventResponse> responses = new ArrayList<ItemSoldEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ItemSoldEventResponse typedResponse = new ItemSoldEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeItemSoldEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(ITEMSOLD_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeItemSoldEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(ITEMSOLD_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<NewItemAddEventResponse> getNewItemAddEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWITEMADD_EVENT, transactionReceipt);
        ArrayList<NewItemAddEventResponse> responses = new ArrayList<NewItemAddEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewItemAddEventResponse typedResponse = new NewItemAddEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeNewItemAddEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(NEWITEMADD_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeNewItemAddEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(NEWITEMADD_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public TransactionReceipt addItem(String name, BigInteger price, String description) {
        final Function function = new Function(
                FUNC_ADDITEM,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(name),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(price),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(description)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] addItem(String name, BigInteger price, String description, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ADDITEM,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(name),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(price),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(description)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForAddItem(String name, BigInteger price, String description) {
        final Function function = new Function(
                FUNC_ADDITEM,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(name),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(price),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(description)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple3<String, BigInteger, String> getAddItemInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ADDITEM,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, BigInteger, String>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue(),
                (String) results.get(2).getValue()
        );
    }

    public Tuple1<BigInteger> getAddItemOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ADDITEM,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
        );
    }

    public TransactionReceipt buyItem(String seller, BigInteger index) {
        final Function function = new Function(
                FUNC_BUYITEM,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(seller),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(index)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] buyItem(String seller, BigInteger index, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_BUYITEM,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(seller),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(index)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForBuyItem(String seller, BigInteger index) {
        final Function function = new Function(
                FUNC_BUYITEM,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(seller),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(index)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getBuyItemInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_BUYITEM,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue()
        );
    }

    public BigInteger getBalance() throws ContractException {
        final Function function = new Function(FUNC_GETBALANCE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public Tuple2<String, String> getRealItem(byte[] hash) throws ContractException {
        final Function function = new Function(FUNC_GETREALITEM,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(hash)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple2<String, String>(
                (String) results.get(0).getValue(),
                (String) results.get(1).getValue());
    }

    public DynamicArray<ItemTrade.Item> getSoldItem() throws ContractException {
        final Function function = new Function(FUNC_GETSOLDITEM,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<ItemTrade.Item>>() {}));
        return executeCallWithSingleValueReturn(function, DynamicArray.class);
    }

    public TransactionReceipt registerAsset(BigInteger choice) {
        final Function function = new Function(
                FUNC_REGISTERASSET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(choice)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] registerAsset(BigInteger choice, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REGISTERASSET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(choice)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRegisterAsset(BigInteger choice) {
        final Function function = new Function(
                FUNC_REGISTERASSET,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(choice)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getRegisterAssetInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REGISTERASSET,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
        );
    }

    public static ItemTrade load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ItemTrade(contractAddress, client, credential);
    }

    public static ItemTrade deploy(Client client, CryptoKeyPair credential, String assetAddress) throws ContractException {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(assetAddress)));
        return deploy(ItemTrade.class, client, credential, getBinary(client.getCryptoSuite()), encodedConstructor);
    }

    public static class Item extends DynamicStruct {
        public BigInteger id;

        public String name;

        public BigInteger price;

        public String description;

        public Boolean isSold;

        public String seller;

        public byte[] hash;

        public Item(Uint256 id, Utf8String name, Uint256 price, Utf8String description, Bool isSold, Address seller, Bytes32 hash) {
            super(id,name,price,description,isSold,seller,hash);
            this.id = id.getValue();
            this.name = name.getValue();
            this.price = price.getValue();
            this.description = description.getValue();
            this.isSold = isSold.getValue();
            this.seller = seller.getValue();
            this.hash = hash.getValue();
        }

        public Item(BigInteger id, String name, BigInteger price, String description, Boolean isSold, String seller, byte[] hash) {
            super(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(id),new org.fisco.bcos.sdk.abi.datatypes.Utf8String(name),new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(price),new org.fisco.bcos.sdk.abi.datatypes.Utf8String(description),new org.fisco.bcos.sdk.abi.datatypes.Bool(isSold),new org.fisco.bcos.sdk.abi.datatypes.Address(seller),new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(hash));
            this.id = id;
            this.name = name;
            this.price = price;
            this.description = description;
            this.isSold = isSold;
            this.seller = seller;
            this.hash = hash;
        }
    }

    public static class ItemSoldEventResponse {
        public TransactionReceipt.Logs log;

        public String seller;

        public String buyer;

        public byte[] hash;
    }

    public static class NewItemAddEventResponse {
        public TransactionReceipt.Logs log;

        public String seller;

        public String name;

        public BigInteger price;
    }
}
