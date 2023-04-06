//SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.6.1;
import "erc20.sol";

contract MyToken is Token {
    // MAX_UINT256 代表最大的 uint256 值
    uint256 private constant MAX_UINT256 = 2**256 - 1;
    // balances 存储每个地址的代币余额
    mapping(address => uint256) public balances;
    // allowed 存储每个地址授权给其他地址的代币数量
    mapping(address => mapping(address => uint256)) public allowed;

    // totalSupply 代表代币的总发行量
    uint256 public totalSupply;

    //storage合约的地址
    address private storage_address;

    /*
NOTE:
下面的变量是可选的，它们不影响核心功能，仅仅为了美观和自定义。
某些钱包/接口可能甚至不会使用这些信息。
*/
    // name 代表代币的名称
    string public name;
    // decimals 代表代币小数位数
    uint8 public decimals;
    // symbol 代表代币的符号
    string public symbol;
    // admin 存储合约管理员的地址
    address private admin;

    // 构造函数，初始化代币的总发行量、名称、小数位数和符号
    constructor(
        uint256 _initialAmount,
        string memory _tokenName,
        uint8 _decimalUnits,
        string memory _tokenSymbol
    ) public {
        // 将所有代币都分配给合约创建者
        balances[msg.sender] = _initialAmount;
        // 更新总发行量
        totalSupply = _initialAmount;
        // 设置代币名称
        name = _tokenName;
        // 设置代币小数位数
        decimals = _decimalUnits;
        // 设置代币符号
        symbol = _tokenSymbol;
        // 设置管理员地址为合约创建者
        admin = msg.sender;
    }

    modifier onlyStorage(){
        require(msg.sender==storage_address,"No right");
        _;
    }

    function setStorage(address storageAddress) external {
        require(storageAddress != address(0),"Invalid address");
        require(storage_address == address(0), "Logic contract address already set");
        storage_address=storageAddress;
    }


    // 转账函数，将指定数量的代币从发送方地址转移到接收方地址
    function transfer(address _to, uint256 _value)
    public
    override
    onlyStorage
    returns (bool success)
    {
        // 当发送方地址余额小于转移数量时，抛出异常
        _to.delegatecall;
        require(
            balances[admin] >= _value,
            "token balance is lower than the value requested"
        );
        // 更新发送方地址和接收方地址的余额
        balances[admin] -= _value;
        balances[_to] += _value;
        // 触发 Transfer 事件
        emit Transfer(admin, _to, _value); //solhint-disable-line indent, no-unused-vars
        return true;
    }

    // 授权转账函数，将指定数量的代币从发送方地址转移到接收方地址，并减少发送方地址授权的代币数量
    function transferFrom(
        address _from,
        address _to,
        uint256 _value
    ) public override onlyStorage returns (bool success) {
        // 获取发送方地址授权给当前地址的代币数量
        uint256 allowance = allowed[_from][admin];
        // 当发送方地址余额小于转移数量或授权数量小于转移数量时，抛出异常
        require(
            balances[_from] >= _value && allowance >= _value,
            "token balance or allowance is lower than amount requested"
        );
        // 更新发送方地址和接收方地址的余额
        balances[_to] += _value;
        balances[_from] -= _value;
        // 当授权数量不是最大值时，减少发送方地址授权的代币数量
        if (allowance < MAX_UINT256) {
            allowed[_from][admin] -= _value;
        }
        // 触发 Transfer 事件
        emit Transfer(_from, _to, _value); //solhint-disable-line indent, no-unused-vars
        return true;
    }

    // 获取指定地址的代币余额
    function balanceOf(address _owner)
    public
    view
    onlyStorage
    override
    returns (uint256 balance)
    {
        return balances[_owner];
    }

    // 授权指定地址可以转移指定数量的代币
    function approve(address owner, uint256 _value)
    public
    override
    onlyStorage
    returns (bool success)
    {
        // 更新发送方地址授权给指定地址的代币数量
        allowed[owner][admin] = _value;
        // 触发 Approval 事件
        emit Approval(admin, owner, _value);
        //solhint-disable-line indent, no-unused-vars
        return true;
    }

    //查看被授权的金额
    function allowance(address _owner,address _spender)
    public
    view
    override
    returns (uint256 remaining)
    {
        return allowed[_owner][_spender];
    }

    //注册
    function register(address user) external onlyStorage {
        approve(user,1000);
        transfer(user, 100);
    }

    //质押货币
    function pledge (address supplier,uint counter) external onlyStorage{
        transferFrom(supplier,admin,counter);
    }

    function reward(address supplier,uint count) external onlyStorage{
        transfer(supplier,count);
    }
}
