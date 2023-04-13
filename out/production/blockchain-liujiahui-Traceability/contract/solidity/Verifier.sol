//SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.6.1;

contract Verifier {
    address public proxy_address;
    address public market_address;
    string private proxy_key;
    string private market_key;
    bool private firstM = false;
    bool private firstY = false;
    mapping(address => bool) public ProxyRight;
    mapping(address => uint256) public MarketRight;



    modifier onlyContract(address usedContract){
        require(usedContract == proxy_address || usedContract == market_address, "No right");
        _;
    }

    function proxy_address_set(address addr, string memory key) external {
        if (!firstY) {
            proxy_address = addr;
            proxy_key = key;
            firstY = true;
        } else {
            if (keccak256(abi.encodePacked(key)) == (keccak256(abi.encodePacked(proxy_key)))) {
                proxy_address = addr;
            }
        }

    }


    function market_address_set(address addr, string memory key) external {
        if (!firstM) {
            market_address = addr;
            market_key = key;
            firstM = true;
        } else {
            if (keccak256(abi.encodePacked(key)) == (keccak256(abi.encodePacked(market_key)))) {
                market_address = addr;
            }
        }
    }

    function Proxy_right_check(address user) external view returns (bool){
        return ProxyRight[user];
    }

    function Proxy_right_set(address admin, address used_contract) external onlyContract(used_contract) {
        ProxyRight[admin] = true;
    }

    function Market_right_set(address user, uint256 choice, address used_contract) external onlyContract(used_contract) {
        MarketRight[user] = choice;
    }

    function Market_right_check(address user) external view returns (uint256){
        return MarketRight[user];
    }
}