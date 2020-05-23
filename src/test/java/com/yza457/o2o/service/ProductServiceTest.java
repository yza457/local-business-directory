package com.yza457.o2o.service;

import com.yza457.o2o.BaseTest;
import com.yza457.o2o.dto.ImageHolder;
import com.yza457.o2o.dto.ProductExecution;
import com.yza457.o2o.entity.Product;
import com.yza457.o2o.entity.ProductCategory;
import com.yza457.o2o.entity.Shop;
import com.yza457.o2o.enums.ProductStateEnum;
import com.yza457.o2o.exceptions.ShopOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws ShopOperationException, FileNotFoundException {
        // create product with shopId = 1 and productCategoryId = 1
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("test product 1");
        product.setProductDesc("test product 1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        // create thumbnail input stream
        File thumbnailFile = new File("C:\\Users\\HP\\Documents\\mooc\\pic_tmp\\minion.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
        // create two detailed images and add them to the product
        File productImg1 = new File("C:\\Users\\HP\\Documents\\mooc\\pic_tmp\\minion.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("C:\\Users\\HP\\Documents\\mooc\\pic_tmp\\dabai.jpg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(), is1));
        productImgList.add(new ImageHolder(productImg2.getName(), is2));
        // add product and check
        ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
    }

    @Test
    public void testModifyProduct() throws ShopOperationException, FileNotFoundException {
        // create a product with shopId = 1 and productCategoryId = 1
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(1L);
        product.setProductId(1L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("official product");
        product.setProductDesc("official product");
        // create thumbnail
        File thumbnailFile = new File("C:\\Users\\HP\\Documents\\mooc\\pic_tmp\\dabai.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
        // create two detail images and add them to productImgList
        File productImg1 = new File("C:\\Users\\HP\\Documents\\mooc\\pic_tmp\\dabai.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("C:\\Users\\HP\\Documents\\mooc\\pic_tmp\\minion.jpg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<>();
        productImgList.add(new ImageHolder(productImg1.getName(), is1));
        productImgList.add(new ImageHolder(productImg2.getName(), is2));
        // modify product and verify
        ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
    }

}
