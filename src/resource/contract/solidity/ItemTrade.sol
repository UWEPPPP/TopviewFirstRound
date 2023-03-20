pragma solidity ^0.6.10;
import  "Asset.sol";
contract ItemTrade {
    Asset set;

    struct Item {
        uint256 id;
        string name;
        uint256 price;
        string description;
        bool isSold;
        address seller;
    }

    mapping( address => Item[] ) private items;

    bool private isProcess = false;

    event NewItemAdd(address indexed seller, string name, uint256 price);
    event ItemSold(address indexed seller, string name, uint256 price,address buyer,bytes32 hash);

    constructor(address assetAddress) public {
        set = Asset(assetAddress);
    }

    modifier OnlySupplier(){
        uint identity=set.judgeIdentity(msg.sender);
        require(identity==1||identity==2, "You Never Register");
        require(identity==1,"You Are Not Consumer");
        _;
    }

    modifier NoReentrant(){
        require(!isProcess,"Renntrant Call");
        isProcess=true;
        _;
        isProcess=false;
    }

    function addItem(string memory name, uint256 price, string memory description) external OnlySupplier returns(uint256){
        uint256 id=items[msg.sender].length;
        Item memory item = Item(id, name, price, description, false,msg.sender);
        items[msg.sender].push(item);
        emit NewItemAdd(msg.sender, name, price);
        return items[msg.sender].length;
    }

    function buyItem(address seller,uint256 index)external payable NoReentrant {
        Item memory item=items[seller][index];
        require(item.isSold==false,"Item is sold");
        require(item.price<=getBalance(),"Not enough money");
        items[seller][index].isSold=true;
        set.decreaseBalance(msg.sender,item.price);
        set.increaseBalance(seller,item.price);
        bytes32 hash= keccak256(abi.encodePacked(item.id,item.name,item.price,item.description,item.seller,msg.sender));
        emit ItemSold(seller, item.name, item.price,msg.sender,hash);
    }

    function verifyItem(uint256 itemId, string memory itemName, string memory itemDescription, uint256 itemPrice, address itemSeller, bytes32 itemHash) external view returns(bool) {
        bytes32 hash = keccak256(abi.encodePacked(itemId, itemName, itemDescription, itemPrice, itemSeller, msg.sender));
        return hash == itemHash;
    }

    function registerAsset(uint256 choice) public{
        set.registerAsset(msg.sender,choice);
    }

    function getBalance() public view returns(uint256){
        return set.getBalance(msg.sender);
    }
}
