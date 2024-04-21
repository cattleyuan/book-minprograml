package com.ab.puzzle.controller.admin;

import com.ab.category.domain.dto.ProductCategoryDTO;
import com.ab.category.domain.vo.ProductCategoryVO;
import com.ab.category.service.ProductCategoryService;
import com.ab.product.dto.ProductQueryDTO;
import com.ab.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/1/31
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "商品分类管理接口")
@RequestMapping("/admin/category")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;
    @GetMapping("/getPcInfo")
    @ApiOperation("获取所有商品分类信息")
    @Cacheable(cacheNames ="productcategory")
    public Result<List<ProductCategoryVO>> getCategoryInfo(){
        List<ProductCategoryVO> productList =productCategoryService.getAllProductCategory();
        return Result.success(productList);
    }

    @PutMapping("/udpate")
    @ApiOperation("根据分类id修改商品分类信息")
    @CacheEvict(cacheNames ="productcategory",allEntries = true)
    public Result updateCategoryInfo(@RequestBody ProductCategoryDTO productCategoryDTO){
        productCategoryService.updateCategoryInfo(productCategoryDTO);
        log.info("修改分类->{}",productCategoryDTO.getCategoryName());
        return Result.success();
    }

    @PostMapping("/add")
    @ApiOperation("新增商品分类信息")
    @CacheEvict(cacheNames ="productcategory",allEntries = true)
    public Result saveCategoryInfo(@RequestParam String name){
        productCategoryService.addCategoryInfo(name);
        log.info("新增分类->{}",name);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除商品分类信息")
    @CacheEvict(cacheNames ="productcategory",allEntries = true)
    public Result removeCategoryInfo(@RequestParam Long categoryId){
        productCategoryService.removeCategory(categoryId);
        log.info("删除分类成功->{}",categoryId);
        return Result.success();
    }

}
