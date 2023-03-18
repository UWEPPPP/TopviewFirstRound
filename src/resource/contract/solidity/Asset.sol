// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.6.1;

contract Asset {

    mapping(address => uint256 ) public balances;
    mapping(address => uint256 ) public users;
    //注册初始资产
    function registerAsset() public {
        address account = msg.sender;
        require(users[account]==0,"Account already register");
        users[account]=1;
        balances[account]=1000;
    }

    function getBalance(address user) external view returns(uint256){
        return balances[user];
    }
}