package com.yza457.o2o.dao;

import com.yza457.o2o.BaseTest;
import com.yza457.o2o.entity.Product;
import com.yza457.o2o.entity.ProductCategory;
import com.yza457.o2o.entity.ProductImg;
import com.yza457.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testAInsertProduct() throws Exception {
        Shop shop1 = new Shop();
        shop1.setShopId(1L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(1L);
        // add 3 product to the shop with shopId = 1
        // all 3 products also has productCategoryId = 1
        Product product1 = new Product();
        product1.setProductName("ut test 1");
        product1.setProductDesc("ut test Desc1");
        product1.setImgAddr("ut test1");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(pc1);
        Product product2 = new Product();
        product2.setProductName("ut test 2");
        product2.setProductDesc("ut test Desc2");
        product2.setImgAddr("ut test2");
        product2.setPriority(2);
        product2.setEnableStatus(0);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(pc1);
        Product product3 = new Product();
        product3.setProductName("test 33");
        product3.setProductDesc("test Desc 33");
        product3.setImgAddr("test 33");
        product3.setPriority(3);
        product3.setEnableStatus(1);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setShop(shop1);
        product3.setProductCategory(pc1);
        // check if insertion is successful
        int effectedNum = productDao.insertProduct(product1);
        assertEquals(1, effectedNum);
        effectedNum = productDao.insertProduct(product2);
        assertEquals(1, effectedNum);
        effectedNum = productDao.insertProduct(product3);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testBQueryProductList() throws Exception {
        Product productCondition = new Product();
        // query by page, expected to return 3 entries
        List<Product> productList = productDao.queryProductList(productCondition, 0, 3);
        assertEquals(3, productList.size());
        // query the number of products which name is "test"
        int count = productDao.queryProductCount(productCondition);
        assertEquals(4, count);
        // use product name return 2 expected result with "ut" in name
        productCondition.setProductName("ut");
        productList = productDao.queryProductList(productCondition, 0, 3);
        assertEquals(2, productList.size());
        count = productDao.queryProductCount(productCondition);
        assertEquals(2, count);
    }

    @Test
    public void testCQueryProductByProductId() throws Exception {
        long productId = 1;
        // initialize two productImg and use these two under product with productId = 1
        // and batch insert into detailed images
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("image 1");
        productImg1.setImgDesc("test image 1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(productId);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("image 2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(productId);
        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectedNum);
        // query product with productID = 1 and check if the size of its productImgList is 2
        Product product = productDao.queryProductById(productId);
        assertEquals(2, product.getProductImgList().size());
        // delete the two product img created
        effectedNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testDUpdateProduct() throws Exception {
        Product product = new Product();
        ProductCategory pc = new ProductCategory();
        Shop shop = new Shop();
        shop.setShopId(1L);
        pc.setProductCategoryId(2L);
        product.setProductId(1L);
        product.setShop(shop);
        product.setProductName("second product");
        product.setProductCategory(pc);
        // modify the name of product with productId = 1
        // check if affected row number is 1
        int effectedNum = productDao.updateProduct(product);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testEUpdateProductCategoryToNull() {
        // set product category id of product with previous the productCategoryId = 2 to null
        int effectedNum = productDao.updateProductCategoryToNull(2L);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testFDeleteShopAuthMap() throws Exception {
        // clear product created by insert method
        Product productCondition = new Product();
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(1L);
        productCondition.setProductCategory(pc);
        // use productCategoryId = 1 to query the three test data created
        List<Product> productList = productDao.queryProductList(productCondition, 0, 3);
        assertEquals(3, productList.size());
        // delete the three products created in a loop
        for (Product p : productList) {
            int effectedNum = productDao.deleteProduct(p.getProductId(), 1);
            assertEquals(1, effectedNum);
        }
    }
}
