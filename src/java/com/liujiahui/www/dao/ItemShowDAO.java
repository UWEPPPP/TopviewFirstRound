package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.ItemPO;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 项显示刀
 *
 * @author 刘家辉
 * @date 2023/04/06
 */
public interface ItemShowDAO {
    /**
     * 查询价格
     *
     * @param min   最小值
     * @param max   最大值
     * @param order 排序
     * @return {@link List}<{@link ItemPO}>
     */
    List<ItemPO> queryByPrice(int min, int max, String order);

    /**
     * 按关键字查询
     *
     * @param keyword 关键字
     * @return {@link List}<{@link ItemPO}>
     */
    List<ItemPO> queryByKeyword(String keyword);

    /**
     * 按类型查询
     *
     * @param type 类型
     * @return {@link List}<{@link ItemPO}>
     */
    List<ItemPO> queryByType(String type);

    /**
     * 查询由卖方
     *
     * @param seller 卖方
     * @return {@link List}<{@link ItemPO}>
     */
    List<ItemPO> queryBySeller(String seller);

    /**
     * 显示所有项
     *
     * @return {@link List}<{@link ItemPO}>
     * @throws SQLException sqlexception异常
     */
    List<ItemPO> showAllItem() throws SQLException;

    /**
     * 更新项目
     *
     * @param oldName     旧名称
     * @param name        名字
     * @param description 描述
     * @param price       价格
     * @throws SQLException sqlexception异常
     */
    void updateItem(String oldName, String name, String description, String price) throws SQLException;

    /**
     * 插入
     *
     * @param name        名字
     * @param price       价格
     * @param description 描述
     * @param userName    用户名
     * @param type        类型
     * @param hash        哈希
     * @throws SQLException sqlexception异常
     */
    void insert(String name, BigInteger price, String description, String userName, int type, String hash) throws SQLException;

    /**
     * 显示消费项目
     *
     * @param accountAddress 账户地址
     * @return {@link Map}<{@link String}, {@link List}<{@link ItemPO}>>
     * @throws SQLException sqlexception异常
     */
    Map<String, List<ItemPO>> showConsumerItem(String accountAddress) throws SQLException;

    /**
     * 得到单项
     *
     * @param hash 哈希
     * @return {@link ItemPO}
     * @throws SQLException sqlexception异常
     */
    ItemPO getSingleItem(String hash) throws SQLException;
}
