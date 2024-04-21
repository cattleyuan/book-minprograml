package com.ab.puzzle.controller.admin;

import com.ab.product.dto.ProductCompletedQueryDTO;
import com.ab.product.dto.ProductQueryDTO;
import com.ab.product.dto.ProductSaveDTO;
import com.ab.product.dto.ProductUpdateDTO;
import com.ab.product.service.ProductService;
import com.ab.product.vo.ProductQueryVO;
import com.ab.product.vo.ProductVO;
import com.ab.result.PageResult;
import com.ab.result.Result;
import com.rabbitmq.client.Return;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/1/31
 */
@RestController("adminProductController")
@RequiredArgsConstructor
@Slf4j
@Validated
@Api(tags = "商品管理相关接口")
@RequestMapping("/admin/product")
public class ProductController {
    private final ProductService productService;
    @PostMapping("/getPdInfo")
    @ApiOperation("分页条件展示商品信息")
    public PageResult<ProductVO> getProductInfo(@RequestBody ProductCompletedQueryDTO queryDTO){
        PageResult<ProductVO> pageResult=productService.getProductInfoInCondition(queryDTO);
        return pageResult;
    }

    @PutMapping("/update")
    @ApiOperation("更新商品信息")
    public Result updateProdutInfo(@NotNull @RequestBody ProductUpdateDTO productUpdateDTO){
        productService.updateProductInfo(productUpdateDTO);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @ApiOperation("批量删除商品")
    public Result removeBlanchProduct(@NotNull @RequestBody List<Long> ids){
        productService.removeBatchByIds(ids);
        log.info("删除成功->{}",ids);
        return Result.success();
    }

    @PostMapping("/add")
    @ApiOperation("新增商品")
    public Result saveProduct(@NotNull @RequestBody ProductSaveDTO productSaveDTO){
        productService.saveProduct(productSaveDTO);
        return Result.success();
    }

}
