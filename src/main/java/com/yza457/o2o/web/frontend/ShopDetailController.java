package com.yza457.o2o.web.frontend;

import com.yza457.o2o.dto.ProductExecution;
import com.yza457.o2o.entity.Product;
import com.yza457.o2o.entity.ProductCategory;
import com.yza457.o2o.entity.Shop;
import com.yza457.o2o.service.ProductCategoryService;
import com.yza457.o2o.service.ProductService;
import com.yza457.o2o.service.ShopService;
import com.yza457.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * get shop info and product list of the shop
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // get shopId from frontend
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if (shopId != -1) {
            // get shop information with shopId
            shop = shopService.getByShopId(shopId);
            // get product category list under the shop
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    /**
     * list all products under the shop
     * the implementation is similar to list shops given shop category
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // get pageIndex, pageSize and shopId from request
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");

        if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
            // try get productCategoryId
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            // try get productName
            String productName = HttpServletRequestUtil.getString(request, "productName");
            // combine search conditions
            Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
            // show list of products based on page conditions and number of products
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    /**
     * combine product search conditions
     *
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            // get all products under the product category
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            // set name in the condition
            productCondition.setProductName(productName);
        }
        // only allow products that are on the shelves
        productCondition.setEnableStatus(1);
        return productCondition;
    }
}

