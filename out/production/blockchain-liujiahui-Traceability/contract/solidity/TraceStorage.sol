//SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.6.10;
pragma experimental ABIEncoderV2;

import "ERC20.sol";

contract TraceStorage {
    MyToken private erc20;

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

    constructor(address erc20Address) public {
        erc20 = MyToken(erc20Address);
    }

    function getSellerItemsIndex(address owner)
    external
    view
    returns (uint256)
    {
        return ItemsBySeller[owner].length;
    }

    function addItem(Item memory items, uint256 counter) external {
        ItemsBySeller[items.seller].push(items);
        erc20.pledge(items.seller, counter);
        user_counter[items.seller][items.hash] += counter;
        ItemByHash[items.hash] = items;
    }

    function getSingleItem(bytes32 hash)
    external
    view
    returns (Item memory item)
    {
        return ItemByHash[hash];
    }

    function updateItem(
        address owner,
        uint256 index,
        uint256 price
    ) external {
        ItemsBySeller[owner][index].price = price;
        ItemByHash[ItemsBySeller[owner][index].hash].price = price;
    }

    function updateStatus(
        address owner,
        uint256 index,
        string memory place,
        uint256 deliver
    ) external {
        Item storage item = ItemsBySeller[owner][index];
        ItemLifeByHash[item.hash].push(ItemLife(now, place, Status(deliver)));
    }

    function getStatus(bytes32 hash)
    external
    view
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
    returns (ItemLife[] memory life)
    {
        return ItemLifeByHash[hash];
    }

    function removeOrRestoreItem(
        uint256 index,
        address owner,
        bool choice
    ) external {
        ItemsBySeller[owner][index].isRemoved = choice;
    }

    function getSellerItem(address seller, uint256 index)
    external
    view
    returns (Item memory item)
    {
        return ItemsBySeller[seller][index];
    }

    function itemIsSold(
        address seller,
        uint256 index,
        bool choice
    ) external {
        ItemsBySeller[seller][index].isSold = choice;
        ItemByHash[ItemsBySeller[seller][index].hash].isSold = choice;
    }

    function getSellerAllItems(address seller)
    external
    view
    returns (Item[] memory items)
    {
        return ItemsBySeller[seller];
    }

    function getRealItem(bytes32 hash)
    external
    view
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
    function registerAsset(address userAddress, uint256 chioce) external {
        address account = userAddress;
        require(UserList[account].exist == 0, "Account already register");
        UserList[account].exist = 1;
        if (chioce == 1) {
            UserList[account].identity = 1;
            erc20.register(userAddress);
            //供应商
        } else {
            UserList[account].identity = 2;
            //消费者
        }
        Balances[account] = 1000;
    }

    function getBalance(address user) external view returns (uint256) {
        return Balances[user];
    }

    function decreaseBalance(address user, uint256 amount) public {
        Balances[user] -= amount;
    }

    function increaseBalance(address user, uint256 amount) public {
        Balances[user] += amount;
    }

    function like_or_report(address supplier, bool choice, bytes32 hash) external {
        uint256 calculate = user_counter[supplier][hash] / 10;
        if (choice) {
            erc20.reward(supplier, calculate);
        } else {
            user_counter[supplier][hash] -= calculate;
        }
    }

    function getToken(address supplier) external view returns (uint256){
        return erc20.balanceOf(supplier);
    }

    function getIdentity(address user) external view returns (uint256) {
        uint256 identity = UserList[user].identity;
        return identity;
    }

    function appeal(address feedbacker, address supplier, uint256 calculate) external {
        erc20.reward(supplier, calculate);
        Balances[feedbacker] -= calculate;
    }


}
