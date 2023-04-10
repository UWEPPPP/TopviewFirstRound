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
    public static final String[] BINARY_ARRAY = {"60806040523480156200001157600080fd5b5060405162000e2b38038062000e2b833981810160405260808110156200003757600080fd5b8151602083018051604051929492938301929190846401000000008211156200005f57600080fd5b9083019060208201858111156200007557600080fd5b82516401000000008111828201881017156200009057600080fd5b82525081516020918201929091019080838360005b83811015620000bf578181015183820152602001620000a5565b50505050905090810190601f168015620000ed5780820380516001836020036101000a031916815260200191505b506040818152602083015192018051929491939192846401000000008211156200011657600080fd5b9083019060208201858111156200012c57600080fd5b82516401000000008111828201881017156200014757600080fd5b82525081516020918201929091019080838360005b83811015620001765781810151838201526020016200015c565b50505050905090810190601f168015620001a45780820380516001836020036101000a031916815260200191505b506040908152336000908152602081815291902088905560028890558651620001d794506004935090870191506200021a565b506005805460ff191660ff84161790558051620001fc9060069060208401906200021a565b5050600780546001600160a01b0319163317905550620002bf915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200025d57805160ff19168380011785556200028d565b828001600101855582156200028d579182015b828111156200028d57825182559160200191906001019062000270565b506200029b9291506200029f565b5090565b620002bc91905b808211156200029b5760008155600101620002a6565b90565b610b5c80620002cf6000396000f3fe608060405234801561001057600080fd5b50600436106100f55760003560e01c80634420e486116100975780639137c1a7116100665780639137c1a71461031f57806395d89b4114610345578063a9059cbb1461034d578063dd62ed3e14610379576100f5565b80634420e486146102795780635c6581651461029f57806370a08231146102cd578063743b3452146102f3576100f5565b806321670f22116100d357806321670f22146101d157806323b872dd146101ff57806327e235e314610235578063313ce5671461025b576100f5565b806306fdde03146100fa578063095ea7b31461017757806318160ddd146101b7575b600080fd5b6101026103a7565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561013c578181015183820152602001610124565b50505050905090810190601f1680156101695780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101a36004803603604081101561018d57600080fd5b506001600160a01b038135169060200135610435565b604080519115158252519081900360200190f35b6101bf6104f0565b60408051918252519081900360200190f35b6101fd600480360360408110156101e757600080fd5b506001600160a01b0381351690602001356104f6565b005b6101a36004803603606081101561021557600080fd5b506001600160a01b0381358116916020810135909116906040013561054f565b6101bf6004803603602081101561024b57600080fd5b50356001600160a01b03166106d9565b6102636106eb565b6040805160ff9092168252519081900360200190f35b6101fd6004803603602081101561028f57600080fd5b50356001600160a01b03166106f4565b6101bf600480360360408110156102b557600080fd5b506001600160a01b038135811691602001351661075a565b6101bf600480360360208110156102e357600080fd5b50356001600160a01b0316610777565b6101fd6004803603604081101561030957600080fd5b506001600160a01b0381351690602001356107e0565b6101fd6004803603602081101561033557600080fd5b50356001600160a01b0316610842565b6101026108f9565b6101a36004803603604081101561036357600080fd5b506001600160a01b038135169060200135610954565b6101bf6004803603604081101561038f57600080fd5b506001600160a01b0381358116916020013516610a71565b6004805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561042d5780601f106104025761010080835404028352916020019161042d565b820191906000526020600020905b81548152906001019060200180831161041057829003601f168201915b505050505081565b6003546000906001600160a01b03163314610482576040805162461bcd60e51b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b6001600160a01b038084166000818152600160209081526040808320600780548716855290835292819020879055915482518781529251939416927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9259281900390910190a350600192915050565b60025481565b6003546001600160a01b03163314610540576040805162461bcd60e51b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b61054a8282610954565b505050565b6003546000906001600160a01b0316331461059c576040805162461bcd60e51b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b6001600160a01b038085166000818152600160209081526040808320600754909516835293815283822054928252819052919091205483118015906105e15750828110155b61061c5760405162461bcd60e51b8152600401808060200182810382526039815260200180610acc6039913960400191505060405180910390fd5b6001600160a01b0380851660009081526020819052604080822080548701905591871681522080548490039055600019811015610683576001600160a01b038086166000908152600160209081526040808320600754909416835292905220805484900390555b836001600160a01b0316856001600160a01b03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040518082815260200191505060405180910390a3506001949350505050565b60006020819052908152604090205481565b60055460ff1681565b6003546001600160a01b0316331461073e576040805162461bcd60e51b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b61074a816103e8610435565b50610756816064610954565b5050565b600160209081526000928352604080842090915290825290205481565b6003546000906001600160a01b031633146107c4576040805162461bcd60e51b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b506001600160a01b031660009081526020819052604090205490565b6003546001600160a01b0316331461082a576040805162461bcd60e51b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b60075461054a9083906001600160a01b03168361054f565b6001600160a01b03811661088f576040805162461bcd60e51b815260206004820152600f60248201526e496e76616c6964206164647265737360881b604482015290519081900360640190fd5b6003546001600160a01b0316156108d75760405162461bcd60e51b8152600401808060200182810382526022815260200180610b056022913960400191505060405180910390fd5b600380546001600160a01b0319166001600160a01b0392909216919091179055565b6006805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561042d5780601f106104025761010080835404028352916020019161042d565b6003546000906001600160a01b031633146109a1576040805162461bcd60e51b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b6007546001600160a01b03166000908152602081905260409020548211156109fa5760405162461bcd60e51b815260040180806020018281038252602f815260200180610a9d602f913960400191505060405180910390fd5b600780546001600160a01b03908116600090815260208181526040808320805488900390558784168084529281902080548801905593548451878152945192949316927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929081900390910190a350600192915050565b6001600160a01b0391821660009081526001602090815260408083209390941682529190915220549056fe746f6b656e2062616c616e6365206973206c6f776572207468616e207468652076616c756520726571756573746564746f6b656e2062616c616e6365206f7220616c6c6f77616e6365206973206c6f776572207468616e20616d6f756e74207265717565737465644c6f67696320636f6e7472616374206164647265737320616c726561647920736574a2646970667358221220881206f6648334b6804b994b0b643b0b4a28186385c8c5185d744bd0ae49538664736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040523480156200001157600080fd5b5060405162000e3638038062000e36833981810160405260808110156200003757600080fd5b8151602083018051604051929492938301929190846401000000008211156200005f57600080fd5b9083019060208201858111156200007557600080fd5b82516401000000008111828201881017156200009057600080fd5b82525081516020918201929091019080838360005b83811015620000bf578181015183820152602001620000a5565b50505050905090810190601f168015620000ed5780820380516001836020036101000a031916815260200191505b506040818152602083015192018051929491939192846401000000008211156200011657600080fd5b9083019060208201858111156200012c57600080fd5b82516401000000008111828201881017156200014757600080fd5b82525081516020918201929091019080838360005b83811015620001765781810151838201526020016200015c565b50505050905090810190601f168015620001a45780820380516001836020036101000a031916815260200191505b506040908152336000908152602081815291902088905560028890558651620001d794506004935090870191506200021a565b506005805460ff191660ff84161790558051620001fc9060069060208401906200021a565b5050600780546001600160a01b0319163317905550620002bf915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200025d57805160ff19168380011785556200028d565b828001600101855582156200028d579182015b828111156200028d57825182559160200191906001019062000270565b506200029b9291506200029f565b5090565b620002bc91905b808211156200029b5760008155600101620002a6565b90565b610b6780620002cf6000396000f3fe608060405234801561001057600080fd5b50600436106100f55760003560e01c8063852d921311610097578063cc8be70e11610066578063cc8be70e14610307578063d4b014261461032d578063e3f36d631461035b578063facc639614610381576100f5565b8063852d92131461027557806394ed4dfd146102a3578063ad8a9731146102c9578063b11b6883146102ff576100f5565b806346b13615116100d357806346b13615146101825780635bfa2796146101a057806360e639f31461021d5780636904e96514610249576100f5565b80630256e278146100fa5780631e7d6248146101145780631f2d486014610142575b600080fd5b6101026103a7565b60408051918252519081900360200190f35b6101406004803603604081101561012a57600080fd5b506001600160a01b0381351690602001356103ad565b005b61016e6004803603604081101561015857600080fd5b506001600160a01b038135169060200135610407565b604080519115158252519081900360200190f35b61018a6104c3565b6040805160ff9092168252519081900360200190f35b6101a86104cc565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101e25781810151838201526020016101ca565b50505050905090810190601f16801561020f5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101406004803603604081101561023357600080fd5b506001600160a01b03813516906020013561055a565b61016e6004803603604081101561025f57600080fd5b506001600160a01b0381351690602001356105bd565b6101026004803603604081101561028b57600080fd5b506001600160a01b03813581169160200135166106dc565b610140600480360360208110156102b957600080fd5b50356001600160a01b0316610707565b61016e600480360360608110156102df57600080fd5b506001600160a01b0381358116916020810135909116906040013561076e565b6101a86108fa565b6101026004803603602081101561031d57600080fd5b50356001600160a01b0316610955565b6101026004803603604081101561034357600080fd5b506001600160a01b03813581169160200135166109bf565b6101026004803603602081101561037157600080fd5b50356001600160a01b03166109dc565b6101406004803603602081101561039757600080fd5b50356001600160a01b03166109ee565b60025481565b6003546001600160a01b031633146103f85760408051636381e58960e11b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b61040282826105bd565b505050565b6003546000906001600160a01b031633146104555760408051636381e58960e11b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b6001600160a01b038084166000818152600160209081526040808320600780548716855290835292819020879055915482518781529251939416927fd1e45707b3f71c77903b61f04c900f772db264b9bf618f1cc3308fb516eb61699281900390910190a350600192915050565b60055460ff1681565b6006805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156105525780601f1061052757610100808354040283529160200191610552565b820191906000526020600020905b81548152906001019060200180831161053557829003601f168201915b505050505081565b6003546001600160a01b031633146105a55760408051636381e58960e11b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b6007546104029083906001600160a01b03168361076e565b6003546000906001600160a01b0316331461060b5760408051636381e58960e11b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b6007546001600160a01b031660009081526020819052604090205482111561066557604051636381e58960e11b815260040180806020018281038252602f815260200180610aa8602f913960400191505060405180910390fd5b600780546001600160a01b03908116600090815260208181526040808320805488900390558784168084529281902080548801905593548451878152945192949316927f18f84334255a242551aa98c68047b5da8063eab9fbeaec1eddeea280044b9ff1929081900390910190a350600192915050565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b6003546001600160a01b031633146107525760408051636381e58960e11b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b61075e816103e8610407565b5061076a8160646105bd565b5050565b6003546000906001600160a01b031633146107bc5760408051636381e58960e11b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b6001600160a01b038085166000818152600160209081526040808320600754909516835293815283822054928252819052919091205483118015906108015750828110155b61083d57604051636381e58960e11b8152600401808060200182810382526039815260200180610ad76039913960400191505060405180910390fd5b6001600160a01b03808516600090815260208190526040808220805487019055918716815220805484900390556000198110156108a4576001600160a01b038086166000908152600160209081526040808320600754909416835292905220805484900390555b836001600160a01b0316856001600160a01b03167f18f84334255a242551aa98c68047b5da8063eab9fbeaec1eddeea280044b9ff1856040518082815260200191505060405180910390a3506001949350505050565b6004805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156105525780601f1061052757610100808354040283529160200191610552565b6003546000906001600160a01b031633146109a35760408051636381e58960e11b8152602060048201526008602482015267139bc81c9a59da1d60c21b604482015290519081900360640190fd5b506001600160a01b031660009081526020819052604090205490565b600160209081526000928352604080842090915290825290205481565b60006020819052908152604090205481565b6001600160a01b038116610a3c5760408051636381e58960e11b815260206004820152600f60248201526e496e76616c6964206164647265737360881b604482015290519081900360640190fd5b6003546001600160a01b031615610a8557604051636381e58960e11b8152600401808060200182810382526022815260200180610b106022913960400191505060405180910390fd5b600380546001600160a01b0319166001600160a01b039290921691909117905556fe746f6b656e2062616c616e6365206973206c6f776572207468616e207468652076616c756520726571756573746564746f6b656e2062616c616e6365206f7220616c6c6f77616e6365206973206c6f776572207468616e20616d6f756e74207265717565737465644c6f67696320636f6e7472616374206164647265737320616c726561647920736574a264697066735822122051fbccd10a7b1f301131848fc4541aa875fdf05fa60be22f4b4bbdb0b82b86d964736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_initialAmount\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"_tokenName\",\"type\":\"string\"},{\"internalType\":\"uint8\",\"name\":\"_decimalUnits\",\"type\":\"uint8\"},{\"internalType\":\"string\",\"name\":\"_tokenSymbol\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_spender\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_spender\",\"type\":\"address\"}],\"name\":\"allowance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"remaining\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"allowed\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"balance\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"balances\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"decimals\",\"outputs\":[{\"internalType\":\"uint8\",\"name\":\"\",\"type\":\"uint8\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"supplier\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"counter\",\"type\":\"uint256\"}],\"name\":\"pledge\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"register\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"supplier\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"count\",\"type\":\"uint256\"}],\"name\":\"reward\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"storageAddress\",\"type\":\"address\"}],\"name\":\"setStorage\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

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

    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));

    protected ContractTokenService(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static ContractTokenService load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ContractTokenService(contractAddress, client, credential);
    }

    public static ContractTokenService deploy(Client client, CryptoKeyPair credential, BigInteger _initialAmount, String _tokenName, BigInteger _decimalUnits, String _tokenSymbol) throws ContractException {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_initialAmount),
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_tokenName),
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