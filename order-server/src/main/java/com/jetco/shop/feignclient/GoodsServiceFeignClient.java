package com.jetco.shop.feignclient;

import com.jetco.shop.bean.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 商品服务通信接口
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-10
 */
@FeignClient("goods-server")
public interface GoodsServiceFeignClient {

    /**
     * 获取商品服务的商品信息
     * @return
     */
    @GetMapping("/goods/getGoodsInfo")
    String getGoodsInfo();

    /**
     * 扣减库存
     * @param goodsId 商品ID
     * @param quantity 商品数量
     * @return
     */
    @PostMapping("/goods/reduce")
    Result<String> reduceStock(@RequestParam("goodsId") Long goodsId, @RequestParam("quantity") Long quantity);

}
