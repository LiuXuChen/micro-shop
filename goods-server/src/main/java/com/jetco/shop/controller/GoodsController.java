package com.jetco.shop.controller;


import com.jetco.shop.bean.Result;
import com.jetco.shop.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 商品服务前端控制器
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-09
 */
@Api(tags = "商品服务API")
@RequestMapping("/goods")
@RestController
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @ApiOperation("获取商品信息")
    @GetMapping("/getGoodsInfo")
    public Result<String> getGoodsInfo() {
        return Result.success(goodsService.getGoodsInfo());
    }

    @ApiOperation("扣减库存")
    @PostMapping("/reduce")
    public Result<String> reduceStock(@RequestParam("goodsId") Long goodsId, @RequestParam("quantity") Long quantity) {
        int count = goodsService.reduceStock(goodsId, quantity);
        if(count > 0) {
            return Result.success("库存扣减成功！");
        }
        return Result.failure("库存扣减失败！");
    }

}
