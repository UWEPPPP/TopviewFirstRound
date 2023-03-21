// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.6.10;
pragma experimental ABIEncoderV2;
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
        bytes32 hash;
    }

    mapping( address => Item[] ) private items;
    mapping( bytes32 => Item) private itemFollow;

    bool private isProcess = false;

    event NewItemAdd(address indexed seller, string name, uint256 price);
    event ItemSold(address indexed seller, address buyer,bytes32 hash);
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
        bytes32 hash= keccak256(abi.encodePacked(id,name,price,description,msg.sender));
        Item memory item = Item(id, name, price, description, false,msg.sender,hash);
        items[msg.sender].push(item);
        itemFollow[hash]=item;
        emit NewItemAdd(msg.sender, name, price);
        return items[msg.sender].length;
    }

    function buyItem(address seller,uint256 index)external NoReentrant {
        Item memory item=items[seller][index];
        require(item.isSold==false,"Item is sold");
        require(item.price<=getBalance(),"Not enough money");
        items[seller][index].isSold=true;
        set.decreaseBalance(msg.sender,item.price);
        set.increaseBalance(seller,item.price);
        emit ItemSold(seller,msg.sender,item.hash);
    }

    function getSoldItem() external view returns(Item[] memory itemSold){
        return items[msg.sender];
    }

    function getRealItem(bytes32 hash) external view returns(string memory name,string memory description){
        name =itemFollow[hash].name;
        description = itemFollow[hash].description;
        return (name,description);
    }

    function registerAsset(uint256 choice) public{
        set.registerAsset(msg.sender,choice);
    }

    function getBalance() public view returns(uint256){
        return set.getBalance(msg.sender);
    }
}
