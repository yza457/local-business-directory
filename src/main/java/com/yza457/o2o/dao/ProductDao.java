package com.yza457.o2o.dao;

import com.yza457.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    /**
     * return a list of Products with list size equals to page size specified
     *
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
                                   @Param("pageSize") int pageSize);

    /**
     * return the number of products
     *
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * return product information based on its id
     *
     * @param productId
     * @return
     */
    Product queryProductById(long productId);

    /**
     * insert a product
     *
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * update product information
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * before deleting product category
     * set product category to null for all associated products
     *
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);

    /**
     * delete a product
     *
     * @param productId
     * @return
     */
    int deleteProduct(@Param("productId") long productId, @Param("shopId") long shopId);
}
