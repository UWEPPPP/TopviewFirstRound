pragma solidity ^0.6.10;
pragma experimental ABIEncoderV2;
contract TraceStorage {
    struct User {
        uint exist;
        uint identity;
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
        Delivered
    }

    mapping(address => uint256) public Balances;
    mapping(address => User) public UserList;
    mapping(address => Item[]) private ItemsBySeller;
    mapping(bytes32 => Item) private ItemByHash;
    mapping(bytes32 => ItemLife) private ItemStatusByHash;

    function getSellerItemsIndex(address owner) external view returns(uint256) {
        return ItemsBySeller[owner].length;
    }

    function addItem(address owner,uint256 id,string memory name, uint256 price, string memory description, uint256 typeSet,address seller,bytes32 hash)external{
        Item memory item= Item(id,name,Type(typeSet),price, description, false, false,seller,hash);
        ItemsBySeller[owner].push(item);
        ItemByHash[hash] = item;
    }

    function getSingleItem(bytes32 hash)external view  returns(Item memory item){
        return ItemByHash[hash];
    }

    function updateItem(address owner,uint256 index, uint256 price) external  {
        ItemsBySeller[owner][index].price = price;
        ItemByHash[ItemsBySeller[owner][index].hash].price=price;
    }

    function updateStatus(address owner,uint256 index, string memory place, uint256 deliver) external {
        Item storage item = ItemsBySeller[owner][index];
        require(item.isSold, "Item is not sold yet");
        ItemStatusByHash[item.hash] = ItemLife(now, place, Status(deliver));
    }

    function getStatus(bytes32 hash) external view  returns (uint256, string memory, uint256) {
        ItemLife storage itemStatus = ItemStatusByHash[hash];
        return (itemStatus.date, itemStatus.place, uint256(itemStatus.status));
    }

    function removeOrRestoreItem(uint256 index,address owner,bool choice)external{
        ItemsBySeller[owner][index].isRemoved = choice;
    }

    function getSellerItem(address seller, uint256 index) external view returns(Item memory item){
        return ItemsBySeller[seller][index];
    }

    function itemIsSold(address seller, uint256 index,bool choice) external{
        ItemsBySeller[seller][index].isSold = choice;
        ItemByHash[ItemsBySeller[seller][index].hash].isSold =choice;
    }

    function getSellerAllItems(address seller) external view  returns (Item[] memory items) {
        return ItemsBySeller[seller];
    }

    function getRealItem(bytes32 hash) external view returns (string memory, string memory, address) {
        Item storage item = ItemByHash[hash];
        return (item.name, item.description, item.seller);
    }

    //注册初始资产
    function registerAsset(address userAddress,uint256 chioce) public {
        address account = userAddress;
        require(UserList[account].exist==0,"Account already register");
        UserList[account].exist=1;
        if(chioce==1){
            UserList[account].identity=1;
            //供应商
        }else{
            UserList[account].identity=2;
            //消费者
        }
        Balances[account]=1000;
    }

    function getBalance(address user) external view returns(uint256){
        return Balances[user];
    }

    function decreaseBalance(address user,uint256 amount) public {
        Balances[user]-=amount;
    }

    function increaseBalance(address user,uint256 amount) public {
        Balances[user]+=amount;
    }

    function getIdentity(address user) external view returns(uint256){
        uint256 identity=UserList[user].identity;
        return identity;
    }
}