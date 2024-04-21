package com.ab.category.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.ab.category.domain.ProductCategory;
import com.ab.category.domain.dto.ProductCategoryDTO;
import com.ab.category.domain.vo.ProductCategoryVO;
import com.ab.category.mapper.ProductCategoryMapper;
import com.ab.category.service.ProductCategoryService;
import com.ab.context.BaseContext;
import com.ab.exception.BaseException;
import com.ab.exception.CategoryLockException;
import com.ab.product.domain.Product;
import com.ab.product.dto.ProductQueryDTO;
import com.ab.product.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
* @author 86150
* @description 针对表【product_category】的数据库操作Service实现
* @createDate 2024-01-31 13:22:35
*/
@Service

public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory>
    implements ProductCategoryService {
    @Autowired
    private  ProductService productService;


    //获取所有分类信息
    @Override
    public List<ProductCategoryVO> getAllProductCategory() {

        List<ProductCategory> productCategoryList = lambdaQuery().list();
        List<ProductCategoryVO> categoryVOList = BeanUtil.copyToList(productCategoryList, ProductCategoryVO.class);
        return categoryVOList;
    }

    @Override
    public void updateCategoryInfo(ProductCategoryDTO productCategoryDTO) {
        if(productCategoryDTO!=null){
            Long adminId = BaseContext.getCurrentId();

            ProductCategory category = new ProductCategory();
            BeanUtil.copyProperties(productCategoryDTO,category);
            category.setUpdateAdminer(adminId);
            updateById(category);
            return;
        }
        else
        throw new BaseException("传入参数为空");
    }

    @Override
    public void addCategoryInfo(String name) {

        Long adminId = BaseContext.getCurrentId();
        addCategory(name, adminId);
    }

    @Override
    public void removeCategory(Long categoryId) {
        List<Product> productList = productService.lambdaQuery().eq(Product::getCategoryId, categoryId).list();

        if (productList.size()!=0)
        throw new CategoryLockException("该分类下有商品未删除");
        removeById(categoryId);
    }

    private void addCategory(String name, Long adminId) {
        ProductCategory category = new ProductCategory();
        category.setCategoryName(name);
        category.setCreateAdminer(adminId);
        category.setUpdateAdminer(adminId);
        save(category);
    }
}




