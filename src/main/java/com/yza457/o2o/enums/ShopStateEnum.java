package com.yza457.o2o.enums;

public enum ShopStateEnum {
    CHECK(0, "under review"),
    OFFLINE(-1, "invalid shop"),
    SUCCESS(1, "operation success"),
    PASS(2, "review ok"),
    INNER_ERROR(-1001, "system error"),
    NULL_SHOPID(-1002, "ShopID is NULL"),
    NULL_SHOP(-1003, "shop is null");

    private int state;
    private String stateInfo;

    private ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopStateEnum stateOf(int state) {
        for (ShopStateEnum stateEnum: values()) {
            if (stateEnum.getState() == state) return stateEnum;
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
