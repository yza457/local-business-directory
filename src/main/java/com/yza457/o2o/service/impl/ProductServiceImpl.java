package com.yza457.o2o.service.impl;

import com.yza457.o2o.dao.ProductDao;
import com.yza457.o2o.dao.ProductImgDao;
import com.yza457.o2o.dto.ImageHolder;
import com.yza457.o2o.dto.ProductExecution;
import com.yza457.o2o.entity.Product;
import com.yza457.o2o.entity.ProductImg;
import com.yza457.o2o.enums.ProductStateEnum;
import com.yza457.o2o.exceptions.ProductOperationException;
import com.yza457.o2o.service.ProductService;
import com.yza457.o2o.util.ImageUtil;
import com.yza457.o2o.util.PageCalculator;
import com.yza457.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;


    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        // convert pageSize and pageIndex into row index in database
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        // return the count of product that meet the same condition
        int count = productDao.queryProductCount(productCondition);
        // write list and count into an instance of ProductExecution
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    @Override
    @Transactional
    // 1.process thumbnail, get thumbnail's relative path and provide to product class
    // 2.write product to db (tb_product) and obtain productId
    // 3.use productId to process detail images in batches
    // 4.batch insert detail images into tb_product_img
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
            throws ProductOperationException {
        // null check
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            // add default properties
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            // by default the product is up
            product.setEnableStatus(1);
            // add thumbnail if it's not null
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {
                // create product information
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("insert product success");
                }
            } catch (Exception e) {
                throw new ProductOperationException("insert product failed: " + e.toString());
            }
            // add product detail images if it's not null
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                addProductImgList(product, productImgHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            // return EMPTY status if the param is empty
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    @Transactional
    // 1. if new thumbnail is passed in, then replace the old thumbnail
    // and set the relative path of new thumbnail to the Product to be modified
    // 2. do the same if new detailed images are passed in
    // 3. delete records related to the product in tb_product_img
    // 4. then update the information in tb_product_img and tb_product
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
                                          List<ImageHolder> productImgHolderList) throws ProductOperationException {
        // null check
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            // update last edit time
            product.setLastEditTime(new Date());
            // if thumbnail is not null then replace the old one
            if (thumbnail != null) {
                // retrieve original information including the file path of original images
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);
            }
            // delete old detail images, then add new images
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                deleteProductImgList(product.getProductId());
                addProductImgList(product, productImgHolderList);
            }
            try {
                // update product information
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("update product information failed");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("update product information failed:" + e.toString());
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * insert thumbnail
     *
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

    /**
     * insert detail images in batches
     *
     * @param product
     * @param productImgHolderList
     */
    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        // obtain the directory path of corresponding shop
        // to hold the images
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        // iterate the image stream, add each image into productImg
        for (ImageHolder productImgHolder : productImgHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }

        // if there exists an image, then add in batches
        if (productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("batch insert product images success");
                }
            } catch (Exception e) {
                throw new ProductOperationException("batch insert product images failed: " + e.toString());
            }
        }
    }

    /**
     * delete all detail images under a product
     *
     * @param productId
     */
    private void deleteProductImgList(long productId) {
        // get original images
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        // delete original images
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        // delete information on product images in database
        productImgDao.deleteProductImgByProductId(productId);
    }
}
