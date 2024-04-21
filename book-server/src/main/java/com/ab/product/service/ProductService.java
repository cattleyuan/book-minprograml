package com.ab.product.service;


import com.ab.category.domain.vo.ProductCategoryVO;
import com.ab.product.domain.Product;
import com.ab.product.dto.ProductCompletedQueryDTO;
import com.ab.product.dto.ProductQueryDTO;
import com.ab.product.dto.ProductSaveDTO;
import com.ab.product.dto.ProductUpdateDTO;
import com.ab.product.vo.ProductQueryVO;
import com.ab.product.vo.ProductVO;
import com.ab.result.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86150
* @description 针对表【product】的数据库操作Service
* @createDate 2024-01-31 13:20:26
*/
public interface ProductService extends IService<Product> {

    List<ProductQueryVO> getProductInfoInSale(ProductQueryDTO productDTO);


    void updateProductInfo(ProductUpdateDTO productUpdateDTO);

    void saveProduct(ProductSaveDTO productSaveDTO);

    PageResult<ProductVO> getProductInfoInCondition(ProductCompletedQueryDTO queryDTO);
}
