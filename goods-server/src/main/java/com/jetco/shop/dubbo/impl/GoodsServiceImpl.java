package com.jetco.shop.dubbo.impl;

import com.jetco.shop.dubbo.GoodsService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * <p>
 * 商品服务实现类
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-10
 */
@DubboService
public class GoodsServiceImpl implements GoodsService {

    @Override
    public String getGoodsInfo() {
        return "name: 苹果18, desc: 这是苹果最新款手机，你值得拥有！";
    }
}
