package com.yza457.o2o.dto;

import com.yza457.o2o.entity.Shop;
import com.yza457.o2o.enums.ShopStateEnum;

import java.util.List;

public class ShopExecution {
    // result status
    private int state;

    // String corresponding to state code
    private String stateInfo;

    // count of shops
    private int count;

    // the shop to be created, modified or deleted
    private Shop shop;

    // list of shops for query
    private List<Shop> shopList;

    public ShopExecution() {

    }

    // constructor for failure cases of Shop operations
    public ShopExecution(ShopStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // constructor for success cases: one shop
    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }

    // constructor for success cases: list of shops
    public ShopExecution(ShopStateEnum stateEnum, Shop shop, List<Shop> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
