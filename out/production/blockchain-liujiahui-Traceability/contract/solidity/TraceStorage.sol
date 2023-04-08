//SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.6.10;
pragma experimental ABIEncoderV2;

import "ERC20.sol";

contract TraceStorage {
    MyToken private erc20;
    address private logic_address;
    address private admin;
    address private proxy;
    string private password_;

    struct User {
        uint256 exist;
        uint256 identity;
        uint256 token;
    }

    struct Item {
        uint256 id;
        string name;
        Type itemType;
        uint256 price;
        string description;
        bool isSold;
        bool isRemoved;
        address seller;
        bytes32 hash;
    }

    struct ItemLife {
        uint256 date;
        string place;
        Status status;
    }

    enum Type {
        Food,
        Apparel,
        Electronic,
        Furniture,
        Others
    }

    enum Status {
        NotDelivered,
        InDelivering,
        Delivered,
        producing,
        inStorage
    }

    mapping(address => uint256) public Balances;
    // Balances 记录每个地址的余额
    mapping(address => User) public UserList;
    // UserList 记录每个账户
    mapping(address => Item[]) private ItemsBySeller;
    // ItemBySeller 记录每个地址的物品
    mapping(bytes32 => Item) private ItemByHash;
    mapping(bytes32 => ItemLife[]) private ItemLifeByHash;
    // 分别是通过hash获取物品数据和生命周期
    mapping(address => mapping(bytes32 => uint256)) public user_counter;

    // user_counter 记录每个地址的token质押数量

    modifier onlyLogicContract(){
        if (logic_address != address(0)) {
            require(msg.sender == logic_address || msg.sender == proxy, "No right");
        }
        _;
    }

    constructor(address erc20Address) public {
        erc20 = MyToken(erc20Address);
        admin = msg.sender;
        erc20.setStorage(address(this));
    }

    function setLogic(address logicAddress, string memory password) external {
        require(logicAddress != address(0), "Invalid");
        string memory test = password_;
        if (logic_address == address(0) || proxy == address(0)) {
            password_ = password;
            logic_address = logicAddress;
        } else {
            if (proxy == address(0)) {
                proxy = logic_address;
            }
            require(keccak256(abi.encodePacked(password)) == keccak256(abi.encodePacked(test)), "No Right");
        }
    }


    function getSellerItemsIndex(address owner)
    external
    view
    onlyLogicContract
    returns (uint256)
    {
        return ItemsBySeller[owner].length;
    }

    function addItem(Item memory items, uint256 counter) external onlyLogicContract {
        ItemsBySeller[items.seller].push(items);
        user_counter[items.seller][items.hash] += counter;
        ItemByHash[items.hash] = items;
        erc20.pledge(items.seller, counter);
    }

    function getSingleItem(bytes32 hash)
    external
    view
    onlyLogicContract
    returns (Item memory item)
    {
        return ItemByHash[hash];
    }

    function updateItem(
        address owner,
        uint256 index,
        uint256 price
    ) external onlyLogicContract {
        ItemsBySeller[owner][index].price = price;
        ItemByHash[ItemsBySeller[owner][index].hash].price = price;
    }

    function updateStatus(
        address owner,
        uint256 index,
        string memory place,
        uint256 deliver
    ) external onlyLogicContract {
        Item storage item = ItemsBySeller[owner][index];
        ItemLifeByHash[item.hash].push(ItemLife(now, place, Status(deliver)));
    }

    function getStatus(bytes32 hash)
    external
    view
    onlyLogicContract
    returns (
        uint256,
        string memory,
        uint256
    )
    {
        uint256 max = ItemLifeByHash[hash].length;
        ItemLife storage itemStatus = ItemLifeByHash[hash][max - 1];
        return (itemStatus.date, itemStatus.place, uint256(itemStatus.status));
    }

    function getWholeLife(bytes32 hash)
    external
    view
    onlyLogicContract
    returns (ItemLife[] memory life)
    {
        return ItemLifeByHash[hash];
    }

    function removeOrRestoreItem(
        uint256 index,
        address owner,
        bool choice
    ) external onlyLogicContract {
        ItemsBySeller[owner][index].isRemoved = choice;
    }

    function getSellerItem(address seller, uint256 index)
    external
    view
    onlyLogicContract
    returns (Item memory item)
    {
        return ItemsBySeller[seller][index];
    }

    function itemIsSold(
        address seller,
        uint256 index,
        bool choice
    ) external onlyLogicContract {
        ItemsBySeller[seller][index].isSold = choice;
        ItemByHash[ItemsBySeller[seller][index].hash].isSold = choice;
    }

    function getSellerAllItems(address seller)
    external
    view
    onlyLogicContract
    returns (Item[] memory items)
    {
        return ItemsBySeller[seller];
    }

    function getRealItem(bytes32 hash)
    external
    view
    onlyLogicContract
    returns (
        string memory,
        string memory,
        address
    )
    {
        Item storage item = ItemByHash[hash];
        return (item.name, item.description, item.seller);
    }

    //注册初始资产
    function registerAsset(address userAddress, uint256 chioce) external onlyLogicContract {
        require(UserList[userAddress].exist == 0, "Account already register");
        address account = userAddress;
        Balances[account] = 1000;
        UserList[account].exist = 1;
        if (chioce == 1) {
            UserList[account].identity = 1;
            erc20.register(userAddress);
            //供应商
        } else {
            UserList[account].identity = 2;
            //消费者
        }

    }

    function getBalance(address user) external view onlyLogicContract returns (uint256) {
        return Balances[user];
    }

    function decreaseBalance(address user, uint256 amount) external onlyLogicContract {
        Balances[user] -= amount;
    }

    function increaseBalance(address user, uint256 amount) external onlyLogicContract {
        Balances[user] += amount;
    }

    function like_or_report(
        address supplier,
        bool choice,
        bytes32 hash
    ) external onlyLogicContract {
        uint256 calculate = user_counter[supplier][hash] / 10;
        returnToken(supplier, hash);
        if (choice) {
            erc20.reward(supplier, calculate);
        } else {
            user_counter[supplier][hash] -= calculate;
        }

    }

    function returnToken(address supplier, bytes32 hash) internal {
        user_counter[supplier][hash] = 0;
        erc20.reward(supplier, user_counter[supplier][hash]);
    }

    function getToken(address supplier) external view onlyLogicContract returns (uint256) {
        return erc20.balanceOf(supplier);
    }

    function getIdentity(address user) external view onlyLogicContract returns (uint256) {
        uint256 identity = UserList[user].identity;
        return identity;
    }

    function appeal(
        address feedbacker,
        address supplier,
        uint256 calculate,
        bool choice
    ) external onlyLogicContract {
        Balances[feedbacker] -= calculate;
        if (choice) {
            erc20.reward(supplier, calculate);
        } else {
            erc20.pledge(supplier, calculate);
        }
    }
}
