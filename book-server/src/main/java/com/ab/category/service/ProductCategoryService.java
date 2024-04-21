package com.ab.category.service;


import com.ab.category.domain.ProductCategory;
import com.ab.category.domain.dto.ProductCategoryDTO;
import com.ab.category.domain.vo.ProductCategoryVO;
import com.ab.product.dto.ProductQueryDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
* @author 86150
* @description 针对表【product_category】的数据库操作Service
* @createDate 2024-01-31 13:22:35
*/
@Lazy

public interface ProductCategoryService extends IService<ProductCategory> {

    List<ProductCategoryVO> getAllProductCategory();

    void updateCategoryInfo(ProductCategoryDTO productCategoryDTO);

    void addCategoryInfo(String name);

    void removeCategory(Long categoryId);
}
