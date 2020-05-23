package com.yza457.o2o.dao;

import com.yza457.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
    /**
     * list detailed images for the product
     *
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(long productId);

    /**
     * batch add detail images to product
     *
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * delete all detail images based in product id
     *
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);
}
