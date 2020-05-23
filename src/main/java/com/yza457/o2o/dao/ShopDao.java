package com.yza457.o2o.dao;

import com.yza457.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    /**
     * query shop by combination of shop name (fuzzy sql shop_name like '%${shopCondition.shopName}%'), shop status,
     * shop category, area id, owner
     * @param shopCondition
     * @param rowIndex return entries starting from this row index
     * @param pageSize the number of entries to be returned
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

    /**
     * return the total number of items returned by queryShopList
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    /**
     * find shop based on ShopId
     * @param shopId
     * @return
     */
    Shop queryByShopId (long shopId);

    /**
     * create a new shop
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * update shop info
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
