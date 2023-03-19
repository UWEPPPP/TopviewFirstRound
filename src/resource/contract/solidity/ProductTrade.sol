// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.6.10;
import  "Asset.sol";
contract ProductTrade {
    Asset set;

    struct Item {
        uint256 id;
        string name;
        uint256 price;
        string description;
        bool isSold;
        address seller;
    }

    mapping( address => Item[] ) public items;

    event NewItemAdd(address indexed seller, string name, uint256 price);
    event ItemSold(address indexed seller, string name, uint256 price,address buyer,bytes32 hash);

    constructor(address assetAddress) public {
        set = Asset(assetAddress);
    }

    function addItem(string memory name, uint256 price, string memory description) public {
        uint256 id=items[msg.sender].length;
        Item memory item = Item(id, name, price, description, false,msg.sender);
        items[msg.sender].push(item);
        emit NewItemAdd(msg.sender, name, price);
    }

    function buyItem(address seller,uint256 index)public payable {
        Item memory item=items[seller][index];
        require(item.isSold==false,"Item is sold");
        require(item.price<=msg.value,"Insufficient funds");
        item.isSold=true;
        payable(seller).transfer(msg.value);
        bytes32 hash=sha256(abi.encodePacked(item.id,item.name,item.price,item.description,item.seller,msg.sender));
        emit ItemSold(seller, item.name, item.price,msg.sender,hash);
    }

    function verifyItem(uint256 itemId, string memory itemName, string memory itemDescription, uint256 itemPrice, address itemSeller, bytes32 itemHash) public view returns(bool) {
        bytes32 hash = sha256(abi.encodePacked(itemId, itemName, itemDescription, itemPrice, itemSeller, msg.sender));
        return hash == itemHash;
    }

    function registerAsset() public{
        set.registerAsset(msg.sender);
    }

    function getBalance() public view returns(uint256){
        return set.getBalance(msg.sender);
    }
}

