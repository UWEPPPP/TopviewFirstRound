package com.liujiahui.www.dao.impl;

import com.liujiahui.www.entity.po.ItemPO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemShowDAOImplTest {

    @org.junit.jupiter.api.Test
    void queryByPrice() {
        List<ItemPO> itemPOS = new ItemShowDAOImpl().queryByKeyword("1");
        System.out.println(itemPOS);
    }

    @org.junit.jupiter.api.Test
    void queryByKeyword() {
    }

    @org.junit.jupiter.api.Test
    void queryByType() {
    }

    @org.junit.jupiter.api.Test
    void queryBySeller() {
    }

    @org.junit.jupiter.api.Test
    void showAllItem() {
    }

    @org.junit.jupiter.api.Test
    void updateItem() {
    }

    @org.junit.jupiter.api.Test
    void insert() {
    }

    @org.junit.jupiter.api.Test
    void showConsumerItem() {
    }

    @org.junit.jupiter.api.Test
    void getSingleItem() {
    }
}