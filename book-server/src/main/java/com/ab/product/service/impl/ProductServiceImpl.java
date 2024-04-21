package com.ab.product.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.ab.category.domain.vo.ProductCategoryVO;
import com.ab.category.service.ProductCategoryService;
import com.ab.context.BaseContext;
import com.ab.exception.BaseException;
import com.ab.product.domain.Product;
import com.ab.product.dto.ProductCompletedQueryDTO;
import com.ab.product.dto.ProductQueryDTO;
import com.ab.product.dto.ProductSaveDTO;
import com.ab.product.dto.ProductUpdateDTO;
import com.ab.product.mapper.ProductMapper;
import com.ab.product.service.ProductService;
import com.ab.product.vo.ProductQueryVO;
import com.ab.product.vo.ProductVO;
import com.ab.result.PageResult;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 86150
 * @description 针对表【product】的数据库操作Service实现
 * @createDate 2024-01-31 13:20:26
 */
@Service
@Slf4j

public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
        implements ProductService {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Override
    public List<ProductQueryVO> getProductInfoInSale(ProductQueryDTO productDTO) {
        List<Product> productList = lambdaQuery().eq(productDTO.getProductZone() != null, Product::getProductZone, productDTO.getProductZone())
                .ge(productDTO.getMinPoints()!=null,Product::getPoints,productDTO.getMinPoints())
                .le(productDTO.getMaxPoints()!=null,Product::getPoints,productDTO.getMaxPoints())
                .eq(productDTO.getCategoryId() != null, Product::getCategoryId, productDTO.getCategoryId())
                .orderByDesc(Product::getDisplayOrder)
                .list();
        List<ProductQueryVO> queryVOList = BeanUtil.copyToList(productList, ProductQueryVO.class);
        return queryVOList;
    }

    @Override
    public void updateProductInfo(ProductUpdateDTO productUpdateDTO) {
        Long categoryId = productUpdateDTO.getCategoryId();
        if(categoryId!=null){
            Optional.ofNullable(productCategoryService.getById(categoryId)).orElseThrow(()->new BaseException("更新异常，无该商品分类"));
        }
        Long adminId= BaseContext.getCurrentId();
        Product product = new Product();
        BeanUtil.copyProperties(productUpdateDTO,product);
        product.setUpdateAdminer(adminId);
        updateById(product);
        log.info("更新商品信息:{},操作人->{}",product.getProductName(),adminId);

    }

    @Override
    public void saveProduct(ProductSaveDTO productSaveDTO) {
        Optional.ofNullable(productCategoryService.getById(productSaveDTO.getCategoryId()))
                .orElseThrow(()->new BaseException("无该商品分类"));
        Long adminId = BaseContext.getCurrentId();
        Product product = new Product();
        BeanUtil.copyProperties(productSaveDTO,product);
        product.setCreateAdminer(adminId);
        product.setUpdateAdminer(adminId);
        save(product);
        log.info("新增商品->{}",product.getProductName());
    }

    @Override
    public PageResult<ProductVO> getProductInfoInCondition(ProductCompletedQueryDTO queryDTO) {
        Page<Product> page=new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());

        Map<String, Integer> orderItem = queryDTO.getOrderItem();
        if (orderItem != null && orderItem.size() > 0) {
            Set<Map.Entry<String, Integer>> entries = orderItem.entrySet();
            for (Map.Entry<String, Integer> entry : entries) {
                page = page.addOrder(new OrderItem(entry.getKey(), entry.getValue() == 1));
            }
        }

        Page<Product> pageResult = lambdaQuery().eq(queryDTO.getProductZone() != null, Product::getProductZone, queryDTO.getProductZone())
                .eq(queryDTO.getCategoryId() != null, Product::getCategoryId, queryDTO.getCategoryId())
                .eq(queryDTO.getStatus() != null, Product::getStatus, queryDTO.getStatus())
                .ge(queryDTO.getMinPoints() != null, Product::getPoints, queryDTO.getMinPoints())
                .le(queryDTO.getMaxPoints() != null, Product::getPoints, queryDTO.getMaxPoints())
                .like(queryDTO.getName() != null, Product::getProductName, queryDTO.getName())
                .page(page);

        PageResult<ProductVO> result = new PageResult();
        List<ProductVO> productVOList = BeanUtil.copyToList(pageResult.getRecords(), ProductVO.class);
        result.setTotal(pageResult.getTotal());
        result.setPages((int) pageResult.getPages());
        result.setRecords(productVOList);

        return result;
    }

}




