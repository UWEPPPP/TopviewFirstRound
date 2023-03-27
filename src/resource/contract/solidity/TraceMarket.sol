pragma solidity ^0.6.10;
pragma experimental ABIEncoderV2;
import "/home/ljh/fisco/console/contracts/solidity/TraceStorage.sol";
contract TraceMarket {

    TraceStorage private  trace;
    bool private isProcessing = false;

    event NewItemAdd(address indexed seller, string name, uint256 price);
    event ItemSold(address indexed seller, address buyer,bytes32 hash);


    constructor (address storageAddress) public{
        trace = TraceStorage(storageAddress);
    }

    modifier onlySupplier() {
        require(judgeIdentity(msg.sender) == 1 || judgeIdentity(msg.sender) == 2, "You are not a registered user");
        require(judgeIdentity(msg.sender) == 1, "You are not a supplier");
        _;
    }

    modifier onlyConsumer() {
        require(judgeIdentity(msg.sender) == 1 || judgeIdentity(msg.sender) == 2, "You are not a registered user");
        require(judgeIdentity(msg.sender) == 2, "You are not a consumer");
        _;
    }

    modifier noReentrant() {
        require(!isProcessing, "Reentrant call");
        isProcessing = true;
        _;
        isProcessing = false;
    }

    function addItem(string memory name, uint256 price, string memory description, uint256 typeSet) external onlySupplier returns (uint256,bytes32) {
        uint256 id = trace.getSellerItemsIndex(msg.sender);
        bytes32 hash = keccak256(abi.encodePacked(id, name, price, description, msg.sender));
        trace.addItem(msg.sender,id,name, price, description,typeSet,msg.sender,hash);
        emit NewItemAdd(msg.sender, name, price);
        return (id,hash);
    }

    function buyItem(address seller, uint256 index) external noReentrant onlyConsumer {
        TraceStorage.Item memory item = trace.getSellerItem(seller,index);
        require(!item.isSold, "Item is sold");
        require(item.price <= getBalance(), "Not enough money");
        trace.itemIsSold(seller,index,true);
        trace.decreaseBalance(msg.sender, item.price);
        trace.increaseBalance(seller, item.price);
        bytes32 hash = keccak256(abi.encodePacked(item.name, item.price, item.description, seller));
        emit ItemSold(seller, msg.sender, hash);
    }

    function getSoldItems() external view onlySupplier returns (TraceStorage.Item[] memory items) {
        return trace.getSellerAllItems(msg.sender);
    }

    function getRealItem(bytes32 hash) external view onlyConsumer returns (string memory, string memory, address) {
        return trace.getRealItem(hash);
    }

    function registerAsset(uint256 choice) external {
        trace.registerAsset(msg.sender,choice);
    }

    function getBalance() public view returns (uint256) {
        return trace.getBalance(msg.sender);
    }

    function updateItem(uint256 index, uint256 price) external onlySupplier {
        trace.updateItem(index,price);
    }

    function updateStatus(uint256 index, string memory place, uint256 deliver) external {
        trace.updateStatus(msg.sender,index,place,deliver);
    }

    function getStatus(bytes32 hash) external view onlyConsumer returns (uint256, string memory, uint256) {
        (uint256 date,string memory place,uint256 status) = trace.getStatus(hash);
        return (date, place, status);
    }

    function refundItem(bytes32 hash,uint256 index) external onlyConsumer {
        TraceStorage.Item memory item = trace.getSingleItem(hash);
        (uint256 date,, )=trace.getStatus(hash);
        require(item.isSold, "Item is not sold yet");
        require((date - now) < 7 days, "Out of date");
        trace.increaseBalance(msg.sender, item.price);
        trace.itemIsSold(msg.sender,index,false);
    }

    function removeItem(uint256 index,bool choice) external {
        trace.removeOrRestoreItem(index,msg.sender,choice);
    }

    function judgeIdentity(address user) private view returns(uint256){
        uint256 identity=trace.getIdentity(user);
        if(identity==1){
            return 1;
            //是供应商
        }else{
            if(identity==2){
                return 2;
                //是消费者
            }else{
                return 0;
                //未注册，为外部恶意调用
            }
        }
    }
}


