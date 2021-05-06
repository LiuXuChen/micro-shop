package com.jetco.shop.controller;


import com.jetco.shop.bean.Result;
import com.jetco.shop.dubbo.GoodsService;
import com.jetco.shop.feignclient.GoodsServiceFeignClient;
import com.jetco.shop.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 订单服务前端控制器
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-10
 */
@Slf4j
@Api(tags = "订单服务API")
@RequestMapping("/order")
@RestController
public class OrderController {

    @DubboReference
    private GoodsService goodsService;

    @Resource
    private GoodsServiceFeignClient goodsServiceFeignClient;

    @Resource
    private OrderService orderService;

    @ApiOperation("/通过Feign调用获取商品信息")
    @GetMapping("/getGoodsInfoByFeign")
    public String getGoodsInfoByFeign() {
        return goodsServiceFeignClient.getGoodsInfo();
    }

    @ApiOperation("/通过Dubbo调用获取商品信息")
    @GetMapping("/getGoodsInfoByDubbo")
    public String getGoodsInfoByDubbo() {
        return goodsService.getGoodsInfo();
    }

    @ApiOperation("/通过Feign调用获取商品信息")
    @PostMapping("/getGoodsInfoByFeignAndPost")
    public String getGoodsInfoByFeignAndPost() {
        return goodsServiceFeignClient.getGoodsInfo();
    }

    @ApiOperation("/通过Dubbo调用获取商品信息")
    @PostMapping("/getGoodsInfoByDubboAndPost")
    public String getGoodsInfoByDubboAndPost() {
        return goodsService.getGoodsInfo();
    }

    @ApiOperation("/下单购物")
    @PostMapping("/shopping")
    public Result<String> shopping(Long accountId, Long goodsId, Long couponId, Long quantity) {
        int count = orderService.shopping(accountId, goodsId, couponId, quantity);
        if(count > 0) {
            return Result.success("订单创建成功！");
        }
        return Result.failure("订单创建失败！");
    }

}
