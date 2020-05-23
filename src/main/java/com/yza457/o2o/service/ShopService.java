package com.yza457.o2o.service;

import com.yza457.o2o.dto.ImageHolder;
import com.yza457.o2o.dto.ShopExecution;
import com.yza457.o2o.entity.Shop;
import com.yza457.o2o.exceptions.ShopOperationException;

public interface ShopService {
    /**
     * return list of shops page by page, based on shopCondition
     * page configuration are pageIndex and pageSize
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

    /**
     * get shop by its id
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * update shop info and thumbnail
     * @param shop
     * @param thumbnail
     * @return
     * @throws ShopOperationException // to roll back transaction in sql database
     */
    ShopExecution modifyShop (Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     *
     * @param shop
     * @param thumbnail
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

}
