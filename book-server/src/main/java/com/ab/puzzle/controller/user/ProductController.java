package com.ab.puzzle.controller.user;

import com.ab.category.domain.vo.ProductCategoryVO;
import com.ab.category.service.ProductCategoryService;
import com.ab.product.dto.ProductQueryDTO;
import com.ab.product.service.ProductService;
import com.ab.product.vo.ProductQueryVO;
import com.ab.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/1/31
 */
@RestController("userProductController")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "商品相关接口")
@RequestMapping("/user/product")
public class ProductController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    @PostMapping("/getPdInfo")
    @ApiOperation("根据商品分类和专区展示在售商品信息")
    public Result<List<ProductQueryVO>> getProductInfo(@RequestBody ProductQueryDTO productDTO){

        List<ProductQueryVO> productList =productService.getProductInfoInSale(productDTO);
        return Result.success(productList);
    }

    @GetMapping("/getPcInfo")
    @ApiOperation("获取所有商品分类信息")
    public Result<List<ProductCategoryVO>> getCategoryInfo(){

        List<ProductCategoryVO> productList =productCategoryService.getAllProductCategory();

        return Result.success(productList);
    }



}
