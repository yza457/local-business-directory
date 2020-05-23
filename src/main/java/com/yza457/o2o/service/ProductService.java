package com.yza457.o2o.service;

import com.yza457.o2o.dto.ImageHolder;
import com.yza457.o2o.dto.ProductExecution;
import com.yza457.o2o.entity.Product;
import com.yza457.o2o.exceptions.ProductOperationException;

import java.util.List;

public interface ProductService {

    /**
     * get list of product with size limited by pageSize
     *
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);



    /**
     * add product, set its thumbnail and detail images
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
            throws ProductOperationException;

    /**
     * return the unique product given it productId
     *
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     * modify product information, thumbnail or detail images
     *
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
            throws ProductOperationException;

}
