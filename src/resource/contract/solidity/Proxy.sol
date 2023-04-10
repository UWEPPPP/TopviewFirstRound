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
    fallback() external  virtual {
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
