package com.yza457.o2o.service;

import com.yza457.o2o.BaseTest;
import com.yza457.o2o.dto.ImageHolder;
import com.yza457.o2o.dto.ShopExecution;
import com.yza457.o2o.entity.Area;
import com.yza457.o2o.entity.PersonInfo;
import com.yza457.o2o.entity.Shop;
import com.yza457.o2o.entity.ShopCategory;
import com.yza457.o2o.enums.ShopStateEnum;
import com.yza457.o2o.exceptions.ShopOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList() {
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(2L);
        shopCondition.setShopCategory(sc);
        ShopExecution se = shopService.getShopList(shopCondition, 2, 2);
        System.out.println("size of shop list is :" + se.getShopList().size());
        System.out.println("total count of shops is :" + se.getCount());
    }

    @Test
    public void testModifyShop() throws ShopOperationException, FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("shop name after modification");
        File shopImg = new File("C:\\Users\\HP\\Documents\\mooc\\pic_tmp\\dabai.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder("dabai.jpg", is);
        ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
        System.out.println("new shop img path is: "+ shopExecution.getShop().getShopImg());
    }

    @Test
    public void testAddShop() throws ShopOperationException, FileNotFoundException {
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
        shop.setShopName("test shop 3");
        shop.setShopDesc("test desc 3");
        shop.setShopAddr("test addr 3");
        shop.setPhone("test phone 3");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("under review");

        File shopImg = new File("C:\\Users\\HP\\Documents\\mooc\\pic_tmp\\minion.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(), is);
        ShopExecution se = shopService.addShop(shop, imageHolder);
        assertEquals(ShopStateEnum.CHECK.getState(), se.getState());

    }
}
