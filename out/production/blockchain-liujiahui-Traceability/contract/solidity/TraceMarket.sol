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

    constructor(address storageAddress, address veri_Address) public {
        trace = TraceStorage(storageAddress);
        admin = msg.sender;
        trace.setLogic(address(this), "liujiahui1Y");
        veri = Verifier(veri_Address);
        test = address(this);
        veri.market_address_set(test, "liujiahui1Y");
        veri.Market_right_set(msg.sender, 3, test);
    }

    modifier onlyAdmin() {
        require(veri.Market_right_check(msg.sender) == 3, "No Right");
        _;
    }

    modifier onlySupplier() {
        require(
            veri.Market_right_check(msg.sender) == 1,
            "You are not a supplier"
        );
        _;
    }

    modifier onlyConsumer() {
        require(
            veri.Market_right_check(msg.sender) == 2,
            "You are not a consumer"
        );
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
        uint256 counter
    ) external onlySupplier returns (uint256, bytes32) {
        uint256 id = trace.getSellerItemsIndex(msg.sender);
        bytes32 hash = keccak256(
            abi.encodePacked(id, name, price, description, msg.sender)
        );
        TraceStorage.Item memory item = TraceStorage.Item(
            id,
            name,
            TraceStorage.Type(typeSet),
            price,
            description,
            false,
            false,
            msg.sender,
            hash
        );
        trace.addItem(item, counter);
        emit NewItemAdd(msg.sender, name, price);
        return (id, hash);
    }

    function buyItem(address seller, uint256 index)
    external
    noReentrant
    onlyConsumer
    returns (
        address,
        address,
        bytes32
    )
    {
        TraceStorage.Item memory item = trace.getSellerItem(seller, index);
        require(!item.isSold, "Item is sold");
        require(item.price <= getBalance(msg.sender), "Not enough money");
        trace.itemIsSold(seller, index, true);
        trace.decreaseBalance(msg.sender, item.price);
        trace.increaseBalance(seller, item.price);
        trace.updateStatus(seller, index, "preparing", 0);
        emit ItemSold(seller, msg.sender, item.hash);
        return (seller, msg.sender, item.hash);
    }

    function getSoldItems()
    external
    view
    onlySupplier
    returns (TraceStorage.Item[] memory items)
    {
        return trace.getSellerAllItems(msg.sender);
    }

    function getRealItem(bytes32 hash)
    external
    view
    onlyConsumer
    returns (
        string memory,
        string memory,
        address
    )
    {
        return trace.getRealItem(hash);
    }

    function registerAsset(uint256 choice) external {
        trace.registerAsset(msg.sender, choice);
        veri.Market_right_set(msg.sender, choice, test);
    }

    function getBalance() public view returns (uint256) {
        return trace.getBalance(msg.sender);
    }

    function updateItem(uint256 index, uint256 price) external onlySupplier {
        trace.updateItem(msg.sender, index, price);
    }

    function updateStatus(
        uint256 index,
        string memory place,
        uint256 deliver,
        address seller
    ) external {
        trace.updateStatus(seller, index, place, deliver);
    }

    function getStatus(bytes32 hash)
    external
    view
    onlyConsumer
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

    function showWholeLife(bytes32 hash)
    external
    view
    onlyConsumer
    returns (TraceStorage.ItemLife[] memory life)
    {
        return trace.getWholeLife(hash);
    }

    function refundItem(bytes32 hash, uint256 index) external onlyConsumer {
        TraceStorage.Item memory item = trace.getSingleItem(hash);
        (uint256 date, , ) = trace.getStatus(hash);
        require(item.isSold, "Item is not sold yet");
        require(now < (date + 7 days), "Out of date");
        trace.increaseBalance(msg.sender, item.price);
        trace.decreaseBalance(item.seller, item.price);
        trace.itemIsSold(item.seller, index, false);
    }

    function removeItem(uint256 index, bool choice) external onlySupplier {
        trace.removeOrRestoreItem(index, msg.sender, choice);
    }

    function handing_feedback(
        address seller,
        bool chioce,
        bytes32 hash
    ) external onlyConsumer {
        trace.like_or_report(seller, chioce, hash);
    }

    function showSupplierToken() external view onlySupplier returns (uint256) {
        return trace.getToken(msg.sender);
    }

    function getSingleItem(bytes32 hash)
    external
    view
    returns (TraceStorage.Item memory item)
    {
        return trace.getSingleItem(hash);
    }

    function resolveAppeal(
        address feedbacker,
        address supplier,
        uint256 token,
        bool choice
    ) external onlyAdmin {
        uint256 count = token / 10;
        trace.appeal(feedbacker, supplier, count, choice);
    }
}
