//SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.6.10;
pragma experimental ABIEncoderV2;
import "TraceStorage.sol";
import "Verifier.sol";

contract Proxy {
    TraceStorage private trace;
    address public admin;
    bool private isProcessing = false;
    address private test;
    address public _implementation; // 逻辑合约地址。implementation合约同一个位置的状态变量类型必须和Proxy合约的相同，不然会报错。
    Verifier private veri;

    modifier onlyAdmin() {
        require(veri.Proxy_right_check(msg.sender), "No right");
        _;
    }

    constructor(
        address implementation_,
        address addr,
        address veri_Address
    ) public {
        require(implementation_ != address(0), "Invalid address");
        require(addr != address(0), "Invalid address");
        _implementation = implementation_;
        trace = TraceStorage(addr);
        trace.setLogic(address(this), "liujiahui1Y");
        veri = Verifier(veri_Address);
        test=address(this);
        veri.proxy_address_set(test,"liujiahui1Y");
        veri.Proxy_right_set(msg.sender,test);
    }


    function upgrade(address newCont) external onlyAdmin {
        _implementation = newCont;
    }

    function register(uint256 choice) external {
        (bool success, ) = address(this).call(
            abi.encodeWithSignature(
                "registerAsset(uint256,address)",
                choice,
                msg.sender
            )
        );
        require(success, "test");
    }

    function getBalance() external returns (uint256) {
        (bool success, bytes memory bt) = address(this).call(
            abi.encodeWithSignature("getBalance(address)", msg.sender)
        );
        require(success, "test");
        return abi.decode(bt, (uint256));
    }

    function addItem(
        string memory name,
        uint256 price,
        string memory description,
        uint256 typeSet,
        uint256 counter
    ) external returns (uint256, bytes32) {
        (bool success, bytes memory bt) = address(this).call(
            abi.encodeWithSignature(
                "addItem(string,uint256,string,uint256,uint256,address)",
                name,
                price,
                description,
                typeSet,
                counter,
                msg.sender
            )
        );
        require(success, "addItem failed");
        return abi.decode(bt, (uint256, bytes32));
    }

    function buyItem(address seller, uint256 index)
    external
    returns (
        address,
        address,
        bytes32
    )
    {
        (bool success, bytes memory bt) = address(this).call(
            abi.encodeWithSignature(
                "buyItem(address,address,uint256)",
                seller,
                msg.sender,
                index
            )
        );
        require(success, "buyItem failed");
        return abi.decode(bt, (address, address, bytes32));
    }

    function getSoldItems()
    external
    view
    returns (TraceStorage.Item[] memory items)
    {
        (bool success, bytes memory bt) = address(this).staticcall(
            abi.encodeWithSignature("getSoldItems(address)", msg.sender)
        );
        require(success, "getSoldItems failed");
        return abi.decode(bt, (TraceStorage.Item[]));
    }

    function getRealItem(bytes32 hash)
    external
    view
    returns (
        string memory,
        string memory,
        address
    )
    {
        (bool success, bytes memory bt) = address(this).staticcall(
            abi.encodeWithSignature(
                "getRealItem(bytes32,address)",
                hash,
                msg.sender
            )
        );
        require(success, "getRealItem failed");
        return abi.decode(bt, (string, string, address));
    }

    function updateItem(uint256 index, uint256 price) external {
        (bool success, ) = address(this).call(
            abi.encodeWithSignature(
                "updateItem(uint256,uint256,address)",
                index,
                price,
                msg.sender
            )
        );
        require(success, "updateItem failed");
    }

    function updateStatus(
        uint256 index,
        string memory place,
        uint256 deliver
    ) external {
        (bool success, ) = address(this).call(
            abi.encodeWithSignature(
                "updateStatus(uint256,string,uint256,address)",
                index,
                place,
                deliver,
                msg.sender
            )
        );
        require(success, "updateStatus failed");
    }

    function getStatus(bytes32 hash)
    external
    view
    returns (
        uint256,
        string memory,
        uint256
    )
    {
        (bool success, bytes memory bt) = address(this).staticcall(
            abi.encodeWithSignature(
                "getStatus(bytes32,address)",
                hash,
                msg.sender
            )
        );
        require(success, "getStatus failed");
        return abi.decode(bt, (uint256, string, uint256));
    }

    function showWholeLife(bytes32 hash)
    external
    view
    returns (TraceStorage.ItemLife[] memory life)
    {
        (bool success, bytes memory bt) = address(this).staticcall(
            abi.encodeWithSignature(
                "showWholeLife(bytes32,address)",
                hash,
                msg.sender
            )
        );
        require(success, "showWholeLife failed");
        return abi.decode(bt, (TraceStorage.ItemLife[]));
    }

    function refundItem(bytes32 hash, uint256 index) external {
        (bool success, ) = address(this).call(
            abi.encodeWithSignature(
                "refundItem(bytes32,uint256,address)",
                hash,
                index,
                msg.sender
            )
        );
        require(success, "refundItem failed");
    }

    function removeItem(uint256 index, bool choice) external {
        (bool success, ) = address(this).call(
            abi.encodeWithSignature(
                "removeItem(uint256,bool,address)",
                index,
                choice,
                msg.sender
            )
        );
        require(success, "removeItem failed");
    }

    function handing_feedback(
        address seller,
        bool choice,
        bytes32 hash
    ) external {
        (bool success, ) = address(this).call(
            abi.encodeWithSignature(
                "handing_feedback(address,bool,bytes32,address)",
                seller,
                choice,
                hash,
                msg.sender
            )
        );
        require(success, "handing_feedback failed");
    }

    function showSupplierToken(address supplier)
    external
    view
    returns (uint256)
    {
        (bool success, bytes memory bt) = address(this).staticcall(
            abi.encodeWithSignature("showSupplierToken(address)", supplier)
        );
        require(success, "showSupplierToken failed");
        return abi.decode(bt, (uint256));
    }

    function getSingleItem(bytes32 hash)
    external
    returns (TraceStorage.Item memory item)
    {
        (bool success, bytes memory bt) = address(this).call(
            abi.encodeWithSignature("getSingleItem(bytes32)", hash)
        );
        require(success, "getSingleItem failed");
        return abi.decode(bt, (TraceStorage.Item));
    }

    function resolveAppeal(
        address feedbacker,
        address supplier,
        uint256 token,
        bool choice
    ) external {
        (bool success, ) = address(this).call(
            abi.encodeWithSignature(
                "resolveAppeal(address,address,uint256,bool,address)",
                feedbacker,
                supplier,
                token,
                choice,
                msg.sender
            )
        );
        require(success, "resolveAppeal failed");
    }

    /**
     * @dev Delegates the current call to `implementation`.
     *
     * This function does not return to its internal call site, it will return directly to the external caller.
     */
    function _delegate(address implementation) internal virtual {
        assembly {
        // Copy msg.data. We take full control of memory in this inline assembly
        // block because it will not return to Solidity code. We overwrite the
        // Solidity scratch pad at memory position 0.
            calldatacopy(0, 0, calldatasize())

        // Call the implementation.
        // out and outsize are 0 because we don't know the size yet.
            let result := delegatecall(
            gas(),
            implementation,
            0,
            calldatasize(),
            0,
            0
            )

        // Copy the returned data.
            returndatacopy(0, 0, returndatasize())

            switch result
            // delegatecall returns 0 on error.
            case 0 {
                revert(0, returndatasize())
            }
            default {
                return(0, returndatasize())
            }
        }
    }

    /**
     * @dev This is a virtual function that should be overridden so it returns the address to which the fallback function
     * and {_fallback} should delegate.
     */

    /**
     * @dev Delegates the current call to the address returned by `_implementation()`.
     *
     * This function does not return to its internal call site, it will return directly to the external caller.
     */
    function _fallback() internal virtual {
        _delegate(_implementation);
    }

    /**
     * @dev Fallback function that delegates calls to the address returned by `_implementation()`. Will run if no other
     * function in the contract matches the call data.
     */
    fallback() external payable virtual {
        _fallback();
    }

    /**
     * @dev Fallback function that delegates calls to the address returned by `_implementation()`. Will run if call data
     * is empty.
     */
    receive() external payable virtual {
        _fallback();
    }

    /**
     * @dev Hook that is called before falling back to the implementation. Can happen as part of a manual `_fallback`
     * call, or as part of the Solidity `fallback` or `receive` functions.
     *
     * If overridden should call `super._beforeFallback()`.
     */
}
