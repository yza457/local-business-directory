package com.yza457.o2o.dao;

import com.yza457.o2o.BaseTest;
import com.yza457.o2o.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testABatchInsertProductImg() throws Exception {
        // add 2 detail images to a product with product id = 1
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("img 1");
        productImg1.setImgDesc("test img 1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(1L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("img 2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1L);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testBQueryProductImgList() {
        // test if the product with productId = 1 has two detail images
        List<ProductImg> productImgList = productImgDao.queryProductImgList(1L);
        assertEquals(2, productImgList.size());
    }

    @Test
    public void testCDeleteProductImgByProductId() throws Exception {
        // deleted 2 detailed images added in the above method
        long productId = 1;
        int effectedNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2, effectedNum);
    }
}
