package com.yza457.o2o.enums;

public enum ProductCategoryStateEnum {
    SUCCESS(1, "create success"),
    INNER_ERROR(-1001, "operation failed"),
    EMPTY_LIST(-1002, "less than 1 product category");

    private int state;

    private String stateInfo;

    private ProductCategoryStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ProductCategoryStateEnum stateOf(int index) {
        for (ProductCategoryStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
