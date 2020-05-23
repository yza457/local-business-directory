package com.yza457.o2o.dto;

import com.yza457.o2o.entity.Product;
import com.yza457.o2o.enums.ProductStateEnum;

import java.util.List;

public class ProductExecution {
    // result state code
    private int state;

    // result state information
    private String stateInfo;

    // count of products
    private int count;

    // the product to be operated
    private Product product;

    // a list of product to be operated (for querying a list of products)
    private List<Product> productList;

    public ProductExecution() {
    }

    // constructor for failure cases
    public ProductExecution(ProductStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // constructor for success cases (one product)
    public ProductExecution(ProductStateEnum stateEnum, Product product) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.product = product;
    }

    // constructor for success cases (list of products)
    public ProductExecution(ProductStateEnum stateEnum, List<Product> productList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productList = productList;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

}
