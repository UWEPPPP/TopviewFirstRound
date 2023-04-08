//SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.6.10;
pragma experimental ABIEncoderV2;
import "Verifier.sol";
import "TraceStorage.sol";
contract TraceMarket {
    TraceStorage private trace;
    address public admin;
    bool private isProcessing = false;
    address public test;
    address public implementation; // 逻辑合约地址。implementation合约同一个位置的状态变量类型必须和Proxy合约的相同，不然会报错。
    Verifier private veri;

    event NewItemAdd(address indexed seller, string name, uint256 price);
    event ItemSold(address indexed seller, address buyer, bytes32 hash);

    constructor(address storageAddress,address veri_Address) public {
        trace = TraceStorage(storageAddress);
        admin = msg.sender;
        trace.setLogic(address(this),"liujiahui1Y");
        veri = Verifier(veri_Address);
        test=address(this);
        veri.market_address_set(test,"liujiahui1Y");
        veri.Market_right_set(msg.sender,3,test);
    }

    modifier onlyAdmin(address admins){
        require(veri.Market_right_check(admins) == 3, "No Right");
        _;
    }

    modifier onlySupplier(address seller) {
        require(veri.Market_right_check(seller) == 1, "You are not a supplier");
        _;
    }

    modifier onlyConsumer(address buyer) {
        require(veri.Market_right_check(buyer) == 2, "You are not a consumer");
        _;
    }

    modifier noReentrant() {
        require(!isProcessing, "Reentrant call");
        isProcessing = true;
        _;
        isProcessing = false;
    }
    function addItem(
        string memory name,
        uint256 price,
        string memory description,
        uint256 typeSet,
        uint256 counter,
        address seller
    ) external onlySupplier(seller) returns (uint256, bytes32) {
        uint256 id = trace.getSellerItemsIndex(seller);
        bytes32 hash = keccak256(
            abi.encodePacked(id, name, price, description, seller)
        );
        TraceStorage.Item memory item = TraceStorage.Item(
            id,
            name,
            TraceStorage.Type(typeSet),
            price,
            description,
            false,
            false,
            seller,
            hash
        );
        trace.addItem(item, counter);
        emit NewItemAdd(seller, name, price);
        return (id, hash);
    }

    function buyItem(address seller, address buyer, uint256 index)
    external
    noReentrant
    onlyConsumer(buyer)
    returns (address, address, bytes32)
    {
        TraceStorage.Item memory item = trace.getSellerItem(seller, index);
        require(!item.isSold, "Item is sold");
        require(item.price <= getBalance(buyer), "Not enough money");
        trace.itemIsSold(seller, index, true);
        trace.decreaseBalance(buyer, item.price);
        trace.increaseBalance(seller, item.price);
        trace.updateStatus(seller, index, "preparing", 0);
        emit ItemSold(seller, buyer, item.hash);
        return (seller, buyer, item.hash);
    }

    function getSoldItems(address seller)
    external
    view
    onlySupplier(seller)
    returns (TraceStorage.Item[] memory items)
    {
        return trace.getSellerAllItems(seller);
    }

    function getRealItem(bytes32 hash, address buyer)
    external
    view
    onlyConsumer(buyer)
    returns (
        string memory,
        string memory,
        address
    )
    {
        return trace.getRealItem(hash);
    }

    function registerAsset(uint256 choice, address user) external {
        trace.registerAsset(user, choice);
        veri.Market_right_set(user,choice,test);
    }

    function getBalance(address owner) public view returns (uint256) {
        return trace.getBalance(owner);
    }

    function updateItem(uint256 index, uint256 price, address seller) external onlySupplier(seller) {
        trace.updateItem(seller, index, price);
    }

    function updateStatus(
        uint256 index,
        string memory place,
        uint256 deliver
    , address seller
    ) external {
        trace.updateStatus(seller, index, place, deliver);
    }

    function getStatus(bytes32 hash, address buyer)
    external
    view
    onlyConsumer(buyer)
    returns (
        uint256,
        string memory,
        uint256
    )
    {
        (uint256 date, string memory place, uint256 status) = trace.getStatus(
            hash
        );
        return (date, place, status);
    }

    function showWholeLife(bytes32 hash, address buyer)
    external
    view
    onlyConsumer(buyer)
    returns (TraceStorage.ItemLife[] memory life)
    {
        return trace.getWholeLife(hash);
    }

    function refundItem(bytes32 hash, uint256 index, address buyer) external onlyConsumer(buyer) {
        TraceStorage.Item memory item = trace.getSingleItem(hash);
        (uint256 date, ,) = trace.getStatus(hash);
        require(item.isSold, "Item is not sold yet");
        require(now < (date + 7 days), "Out of date");
        trace.increaseBalance(buyer, item.price);
        trace.decreaseBalance(item.seller, item.price);
        trace.itemIsSold(item.seller, index, false);
    }

    function removeItem(uint256 index, bool choice, address seller) external onlySupplier(seller) {
        trace.removeOrRestoreItem(index, msg.sender, choice);
    }

    function handing_feedback(address seller, bool chioce, bytes32 hash, address buyer) external onlyConsumer(buyer) {
        trace.like_or_report(seller, chioce, hash);
    }

    function showSupplierToken(address supplier) external view onlySupplier(supplier) returns (uint256){
        return trace.getToken(supplier);
    }

    function getSingleItem(bytes32 hash) external view returns (TraceStorage.Item memory item){
        return trace.getSingleItem(hash);
    }

    function resolveAppeal(address feedbacker, address supplier, uint256 token, bool choice, address admins) external onlyAdmin(admins) {
        uint256 count = token / 10;
        trace.appeal(feedbacker, supplier, count, choice);
    }
}
