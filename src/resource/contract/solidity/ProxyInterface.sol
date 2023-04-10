//SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.6.10;
pragma experimental ABIEncoderV2;

import "TraceStorage.sol";
import "Verifier.sol";

interface ProxyInterface {
    function addItem(
        string memory name,
        uint256 price,
        string memory description,
        uint256 typeSet,
        uint256 counter
    ) external returns (uint256, bytes32);

    function buyItem(address seller, uint256 index) external returns (address, address, bytes32);

    function getSoldItems() external view returns (TraceStorage.Item[] memory items);

    function getRealItem(bytes32 hash) external view returns (string memory, string memory, address);

    function registerAsset(uint256 choice) external;

    function getBalance() external view returns (uint256);

    function updateItem(uint256 index, uint256 price) external;

    function updateStatus(uint256 index, string memory place, uint256 deliver, address seller) external;

    function getStatus(bytes32 hash) external view returns (uint256, string memory, uint256);

    function showWholeLife(bytes32 hash) external view returns (TraceStorage.ItemLife[] memory life);

    function refundItem(bytes32 hash, uint256 index) external;

    function removeItem(uint256 index, bool choice) external;

    function handing_feedback(address seller, bool chioce, bytes32 hash) external;

    function showSupplierToken() external view returns (uint256);

    function getSingleItem(bytes32 hash) external view returns (TraceStorage.Item memory item);

    function resolveAppeal(address feedbacker, address supplier, uint256 token, bool choice) external;
}