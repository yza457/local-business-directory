package com.yza457.o2o.dao;

import com.yza457.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    /**
     * return a List of ProductCategory using shop id
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(long shopId);

    /**
     * add product category in batch
     * @param productCategoryList
     * @return number of rows inserted
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * delete product category based on product category
     * and its associated shop id
     * @param productCategoryId
     * @param shopId
     * @return effectedNum
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId,
                              @Param("shopId") long shopId); // use @Param so MyBatis could identify
}
