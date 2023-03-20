// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.6.1;

contract Asset {
    struct User{
        uint exist;
        uint identity;
    }

    mapping(address => uint256 ) public balances;
    mapping(address => User ) public userList;


    //注册初始资产
    function registerAsset(address userAddress,uint256 chioce) public {
        address account = userAddress;
        require(userList[account].exist==0,"Account already register");
        userList[account].exist=1;
        if(chioce==1){
            userList[account].identity=1;
            //供应商
        }else{
            userList[account].identity=2;
            //消费者
        }
        balances[account]=1000;
    }

    function getBalance(address user) external view returns(uint256){
        return balances[user];
    }

    function decreaseBalance(address user,uint256 amount) public {
        balances[user]-=amount;
    }

    function increaseBalance(address user,uint256 amount) public {
        balances[user]+=amount;
    }



    function judgeIdentity(address user) external view returns(uint256){
        uint256 identity=userList[user].identity;
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