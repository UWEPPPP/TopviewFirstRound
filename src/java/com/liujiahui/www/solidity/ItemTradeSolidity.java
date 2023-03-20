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
public class ItemTradeSolidity extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040526002805460ff1916905534801561001a57600080fd5b50604051610f63380380610f638339818101604052602081101561003d57600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055610ef48061006f6000396000f3fe60806040526004361061004a5760003560e01c80630d044b1c1461004f57806312065fe0146101b4578063582fd617146101db5780639f37092a14610207578063a3e54b3e14610233575b600080fd5b34801561005b57600080fd5b506101a0600480360360c081101561007257600080fd5b81359190810190604081016020820135600160201b81111561009357600080fd5b8201836020820111156100a557600080fd5b803590602001918460018302840111600160201b831117156100c657600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561011857600080fd5b82018360208201111561012a57600080fd5b803590602001918460018302840111600160201b8311171561014b57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505082359350505060208101356001600160a01b03169060400135610371565b604080519115158252519081900360200190f35b3480156101c057600080fd5b506101c961046b565b60408051918252519081900360200190f35b3480156101e757600080fd5b50610205600480360360208110156101fe57600080fd5b50356104e9565b005b6102056004803603604081101561021d57600080fd5b506001600160a01b038135169060200135610552565b34801561023f57600080fd5b506101c96004803603606081101561025657600080fd5b810190602081018135600160201b81111561027057600080fd5b82018360208201111561028257600080fd5b803590602001918460018302840111600160201b831117156102a357600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092958435959094909350604081019250602001359050600160201b8111156102fd57600080fd5b82018360208201111561030f57600080fd5b803590602001918460018302840111600160201b8311171561033057600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610b1c945050505050565b6000808787878787336040516020018087815260200186805190602001908083835b602083106103b25780518252601f199092019160209182019101610393565b51815160209384036101000a600019018019909216911617905288519190930192880191508083835b602083106103fa5780518252601f1990920191602091820191016103db565b51815160209384036101000a6000190180199092169116179052920195865250606093841b6bffffffffffffffffffffffff19908116868301529290931b909116603484015250604080518084036028018152604890930190528151910120959095149a9950505050505050505050565b600080546040805163f8b2cb4f60e01b815233600482015290516001600160a01b039092169163f8b2cb4f91602480820192602092909190829003018186803b1580156104b757600080fd5b505afa1580156104cb573d6000803e3d6000fd5b505050506040513d60208110156104e157600080fd5b505190505b90565b600080546040805163b7785afb60e01b81523360048201526024810185905290516001600160a01b039092169263b7785afb9260448084019382900301818387803b15801561053757600080fd5b505af115801561054b573d6000803e3d6000fd5b5050505050565b60025460ff161561059b576040805162461bcd60e51b815260206004820152600e60248201526d14995b9b9d1c985b9d0810d85b1b60921b604482015290519081900360640190fd5b6002805460ff191660011790556105b0610de5565b6001600160a01b03831660009081526001602052604090208054839081106105d457fe5b90600052602060002090600502016040518060c001604052908160008201548152602001600182018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106905780601f1061066557610100808354040283529160200191610690565b820191906000526020600020905b81548152906001019060200180831161067357829003601f168201915b505050918352505060028281015460208084019190915260038401805460408051601f6000196101006001861615020190931695909504918201849004840285018401815281855290940193909183018282801561072f5780601f106107045761010080835404028352916020019161072f565b820191906000526020600020905b81548152906001019060200180831161071257829003601f168201915b50505091835250506004919091015460ff81161515602083015261010090046001600160a01b03166040909101526080810151909150156107a6576040805162461bcd60e51b815260206004820152600c60248201526b125d195b481a5cc81cdbdb1960a21b604482015290519081900360640190fd5b6107ae61046b565b816040015111156107f9576040805162461bcd60e51b815260206004820152601060248201526f4e6f7420656e6f756768206d6f6e657960801b604482015290519081900360640190fd5b6001600160a01b038316600090815260016020819052604090912080548490811061082057fe5b60009182526020822060046005909202018101805493151560ff199094169390931790925580546040808501518151600162fa96b760e01b0319815233958101959095526024850152516001600160a01b039091169263ff05694992604480830193919282900301818387803b15801561089957600080fd5b505af11580156108ad573d6000803e3d6000fd5b5050600080546040808601518151635b86f59960e01b81526001600160a01b038a811660048301526024820192909252915192169450635b86f5999350604480820193929182900301818387803b15801561090757600080fd5b505af115801561091b573d6000803e3d6000fd5b50505050600081600001518260200151836040015184606001518560a00151336040516020018087815260200186805190602001908083835b602083106109735780518252601f199092019160209182019101610954565b51815160209384036101000a60001901801990921691161790529201878152865190830192870191508083835b602083106109bf5780518252601f1990920191602091820191016109a0565b6001836020036101000a038019825116818451168082178552505050505050905001836001600160a01b03166001600160a01b031660601b8152601401826001600160a01b03166001600160a01b031660601b81526014019650505050505050604051602081830303815290604052805190602001209050836001600160a01b03167f17448fd9a76ee7763e78c8a5a143f9274ee5c69cc7668146b32e49187da059298360200151846040015133856040518080602001858152602001846001600160a01b03166001600160a01b03168152602001838152602001828103825286818151815260200191508051906020019080838360005b83811015610acf578181015183820152602001610ab7565b50505050905090810190601f168015610afc5780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a250506002805460ff191690555050565b6000805460408051633cb715a960e21b8152336004820152905183926001600160a01b03169163f2dc56a4916024808301926020929190829003018186803b158015610b6757600080fd5b505afa158015610b7b573d6000803e3d6000fd5b505050506040513d6020811015610b9157600080fd5b505190506001811480610ba45750806002145b610bea576040805162461bcd60e51b81526020600482015260126024820152712cb7ba902732bb32b9102932b3b4b9ba32b960711b604482015290519081900360640190fd5b80600114610c36576040805162461bcd60e51b81526020600482015260146024820152732cb7ba9020b932902737ba1021b7b739bab6b2b960611b604482015290519081900360640190fd5b33600090815260016020526040902054610c4e610de5565b506040805160c0810182528281526020808201898152828401899052606083018890526000608084018190523360a08501819052815260018084529481208054808701825590825290839020845160059092020190815590518051939485949293610cbe93918501920190610e26565b506040820151600282015560608201518051610ce4916003840191602090910190610e26565b5060808201516004909101805460a0909301516001600160a01b031661010002610100600160a81b031992151560ff199094169390931791909116919091179055604080516020808201899052828252895192820192909252885133927f2db22df1631edf2d77f9b36c2a4277805c00cc152fddb472fd9d0d23c9dd3950928b928b928291606083019186019080838360005b83811015610d8f578181015183820152602001610d77565b50505050905090810190601f168015610dbc5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a250503360009081526001602052604090205495945050505050565b6040518060c001604052806000815260200160608152602001600081526020016060815260200160001515815260200160006001600160a01b031681525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610e6757805160ff1916838001178555610e94565b82800160010185558215610e94579182015b82811115610e94578251825591602001919060010190610e79565b50610ea0929150610ea4565b5090565b6104e691905b80821115610ea05760008155600101610eaa56fea26469706673582212208e10b0dd1b507e713c8431892bc02c3769466d988242c1117a1bbc6183cef83064736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040526002805460ff1916905534801561001a57600080fd5b50604051610f65380380610f658339818101604052602081101561003d57600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055610ef68061006f6000396000f3fe60806040526004361061004a5760003560e01c8063078806f31461004f57806314e6bf0d1461019f5780632a3db26b146101cb578063c0f25f4314610330578063eda1cd8a14610345575b600080fd5b34801561005b57600080fd5b5061018d6004803603606081101561007257600080fd5b810190602081018135600160201b81111561008c57600080fd5b82018360208201111561009e57600080fd5b803590602001918460018302840111600160201b831117156100bf57600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092958435959094909350604081019250602001359050600160201b81111561011957600080fd5b82018360208201111561012b57600080fd5b803590602001918460018302840111600160201b8311171561014c57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610371945050505050565b60408051918252519081900360200190f35b3480156101ab57600080fd5b506101c9600480360360208110156101c257600080fd5b503561063c565b005b3480156101d757600080fd5b5061031c600480360360c08110156101ee57600080fd5b81359190810190604081016020820135600160201b81111561020f57600080fd5b82018360208201111561022157600080fd5b803590602001918460018302840111600160201b8311171561024257600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561029457600080fd5b8201836020820111156102a657600080fd5b803590602001918460018302840111600160201b831117156102c757600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505082359350505060208101356001600160a01b031690604001356106a5565b604080519115158252519081900360200190f35b34801561033c57600080fd5b5061018d61079f565b6101c96004803603604081101561035b57600080fd5b506001600160a01b03813516906020013561081d565b600080546040805163348de0b160e11b8152336004820152905183926001600160a01b03169163691bc162916024808301926020929190829003018186803b1580156103bc57600080fd5b505afa1580156103d0573d6000803e3d6000fd5b505050506040513d60208110156103e657600080fd5b5051905060018114806103f95750806002145b6104405760408051636381e58960e11b81526020600482015260126024820152712cb7ba902732bb32b9102932b3b4b9ba32b960711b604482015290519081900360640190fd5b8060011461048d5760408051636381e58960e11b81526020600482015260146024820152732cb7ba9020b932902737ba1021b7b739bab6b2b960611b604482015290519081900360640190fd5b336000908152600160205260409020546104a5610de7565b506040805160c0810182528281526020808201898152828401899052606083018890526000608084018190523360a0850181905281526001808452948120805480870182559082529083902084516005909202019081559051805193948594929361051593918501920190610e28565b50604082015160028201556060820151805161053b916003840191602090910190610e28565b5060808201516004909101805460a0909301516001600160a01b031661010002610100600160a81b031992151560ff199094169390931791909116919091179055604080516020808201899052828252895192820192909252885133927f80e863492b63f6a162c0f7ef99ef9f59519410da2dc51148db147b762173afeb928b928b928291606083019186019080838360005b838110156105e65781810151838201526020016105ce565b50505050905090810190601f1680156106135780820380516001836020036101000a031916815260200191505b50935050505060405180910390a250503360009081526001602052604090205495945050505050565b60008054604080516311da8d2b60e31b81523360048201526024810185905290516001600160a01b0390921692638ed469589260448084019382900301818387803b15801561068a57600080fd5b505af115801561069e573d6000803e3d6000fd5b5050505050565b6000808787878787336040516020018087815260200186805190602001908083835b602083106106e65780518252601f1990920191602091820191016106c7565b51815160209384036101000a600019018019909216911617905288519190930192880191508083835b6020831061072e5780518252601f19909201916020918201910161070f565b51815160209384036101000a6000190180199092169116179052920195865250606093841b6bffffffffffffffffffffffff19908116868301529290931b909116603484015250604080518084036028018152604890930190528151910120959095149a9950505050505050505050565b6000805460408051630fc6ebe160e21b815233600482015290516001600160a01b0390921691633f1baf8491602480820192602092909190829003018186803b1580156107eb57600080fd5b505afa1580156107ff573d6000803e3d6000fd5b505050506040513d602081101561081557600080fd5b505190505b90565b60025460ff16156108675760408051636381e58960e11b815260206004820152600e60248201526d14995b9b9d1c985b9d0810d85b1b60921b604482015290519081900360640190fd5b6002805460ff1916600117905561087c610de7565b6001600160a01b03831660009081526001602052604090208054839081106108a057fe5b90600052602060002090600502016040518060c001604052908160008201548152602001600182018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561095c5780601f106109315761010080835404028352916020019161095c565b820191906000526020600020905b81548152906001019060200180831161093f57829003601f168201915b505050918352505060028281015460208084019190915260038401805460408051601f600019610100600186161502019093169590950491820184900484028501840181528185529094019390918301828280156109fb5780601f106109d0576101008083540402835291602001916109fb565b820191906000526020600020905b8154815290600101906020018083116109de57829003601f168201915b50505091835250506004919091015460ff81161515602083015261010090046001600160a01b0316604090910152608081015190915015610a735760408051636381e58960e11b815260206004820152600c60248201526b125d195b481a5cc81cdbdb1960a21b604482015290519081900360640190fd5b610a7b61079f565b81604001511115610ac75760408051636381e58960e11b815260206004820152601060248201526f4e6f7420656e6f756768206d6f6e657960801b604482015290519081900360640190fd5b6001600160a01b0383166000908152600160208190526040909120805484908110610aee57fe5b60009182526020822060046005909202018101805493151560ff1990941693909317909255805460408085015181516311185c2760e01b815233958101959095526024850152516001600160a01b03909116926311185c2792604480830193919282900301818387803b158015610b6457600080fd5b505af1158015610b78573d6000803e3d6000fd5b5050600080546040808601518151630ec12e8560e21b81526001600160a01b038a811660048301526024820192909252915192169450633b04ba149350604480820193929182900301818387803b158015610bd257600080fd5b505af1158015610be6573d6000803e3d6000fd5b50505050600081600001518260200151836040015184606001518560a00151336040516020018087815260200186805190602001908083835b60208310610c3e5780518252601f199092019160209182019101610c1f565b51815160209384036101000a60001901801990921691161790529201878152865190830192870191508083835b60208310610c8a5780518252601f199092019160209182019101610c6b565b6001836020036101000a038019825116818451168082178552505050505050905001836001600160a01b03166001600160a01b031660601b8152601401826001600160a01b03166001600160a01b031660601b81526014019650505050505050604051602081830303815290604052805190602001209050836001600160a01b03167f2dc3a6e22bb6987009c0ed28d9c22b5f1ffb46dc2d6b654b2505dd1b3d59a9648360200151846040015133856040518080602001858152602001846001600160a01b03166001600160a01b03168152602001838152602001828103825286818151815260200191508051906020019080838360005b83811015610d9a578181015183820152602001610d82565b50505050905090810190601f168015610dc75780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a250506002805460ff191690555050565b6040518060c001604052806000815260200160608152602001600081526020016060815260200160001515815260200160006001600160a01b031681525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610e6957805160ff1916838001178555610e96565b82800160010185558215610e96579182015b82811115610e96578251825591602001919060010190610e7b565b50610ea2929150610ea6565b5090565b61081a91905b80821115610ea25760008155600101610eac56fea2646970667358221220df04ce1f843c1364b32a3bf7fd7dcb3e852949f0139966b3e60d84ab3aa6f61864736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"assetAddress\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"seller\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"price\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"buyer\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"ItemSold\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"seller\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"price\",\"type\":\"uint256\"}],\"name\":\"NewItemAdd\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"price\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"description\",\"type\":\"string\"}],\"name\":\"addItem\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"seller\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"buyItem\",\"outputs\":[],\"stateMutability\":\"payable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getBalance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"choice\",\"type\":\"uint256\"}],\"name\":\"registerAsset\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"itemId\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"itemName\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"itemDescription\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"itemPrice\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"itemSeller\",\"type\":\"address\"},{\"internalType\":\"bytes32\",\"name\":\"itemHash\",\"type\":\"bytes32\"}],\"name\":\"verifyItem\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_ADDITEM = "addItem";

    public static final String FUNC_BUYITEM = "buyItem";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_REGISTERASSET = "registerAsset";

    public static final String FUNC_VERIFYITEM = "verifyItem";

    public static final Event ITEMSOLD_EVENT = new Event("ItemSold",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event NEWITEMADD_EVENT = new Event("NewItemAdd",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    protected ItemTradeSolidity(String contractAddress, Client client, CryptoKeyPair credential) {
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
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
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

    public Boolean verifyItem(BigInteger itemId, String itemName, String itemDescription, BigInteger itemPrice, String itemSeller, byte[] itemHash) throws ContractException {
        final Function function = new Function(FUNC_VERIFYITEM,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(itemId),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(itemName),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(itemDescription),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(itemPrice),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(itemSeller),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(itemHash)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public static ItemTradeSolidity load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ItemTradeSolidity(contractAddress, client, credential);
    }

    public static ItemTradeSolidity deploy(Client client, CryptoKeyPair credential, String assetAddress) throws ContractException {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(assetAddress)));
        return deploy(ItemTradeSolidity.class, client, credential, getBinary(client.getCryptoSuite()), encodedConstructor);
    }

    public static class ItemSoldEventResponse {
        public TransactionReceipt.Logs log;

        public String seller;

        public String name;

        public BigInteger price;

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