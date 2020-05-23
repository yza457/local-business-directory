package com.yza457.o2o.dao;

import com.yza457.o2o.BaseTest;
import com.yza457.o2o.entity.Area;
import com.yza457.o2o.entity.PersonInfo;
import com.yza457.o2o.entity.Shop;
import com.yza457.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryShopListAndQueryShopCount() {
        Shop shopCondition = new Shop();
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(2L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);

        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 7);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("the size of shop list is: "+ shopList.size());
        System.out.println("total number of shops is: "+ count);

//        System.out.println("---Add Shop Category Condition---");
//        ShopCategory sc = new ShopCategory();
//        sc.setShopCategoryId(2L);
//        shopCondition.setShopCategory(sc);
//        shopList = shopDao.queryShopList(shopCondition, 0, 2);
//        count = shopDao.queryShopCount(shopCondition);
//        System.out.println("the size of shop list is: "+ shopList.size());
//        System.out.println("total number of shops is: "+ count);
    }


    @Test
    public void testQueryByShopId() {
        long shopId = 1L;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println("areaId: " + shop.getArea().getAreaId());
        System.out.println("areaName: " + shop.getArea().getAreaName());
    }

    @Test
    public void testInsertShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1L);

        // set shop properties
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("test shop");
        shop.setShopDesc("test desc");
        shop.setShopAddr("test addr");
        shop.setPhone("test phone");
        shop.setShopImg("test img");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("under review");

        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testUpdateShop() {
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopDesc("test desc update");
        shop.setShopAddr("test addr update");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }
}
