package com.jetco.shop.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jetco.shop.config.GoodsConfig;
import com.jetco.shop.entity.Goods;
import com.jetco.shop.enums.ShopCodeEnum;
import com.jetco.shop.exception.ParameterException;
import com.jetco.shop.mapper.GoodsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 商品服务类
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-11
 */
@Service
public class GoodsService extends ServiceImpl<GoodsMapper, Goods> {

    @Resource
    private GoodsConfig goodsConfig;

    @Resource
    private GoodsMapper goodsMapper;

    public String getGoodsInfo() {
        return "name: " + goodsConfig.getName() + ", desc: " + goodsConfig.getDesc();
    }

    @Transactional(rollbackFor = Exception.class)
    public int reduceStock(Long goodsId, Long quantity) {
        Goods goods = goodsMapper.selectById(goodsId);
        if((goods.getGoodsNumber() - quantity )< 0) {
            throw new ParameterException("商品编号为：" + goodsId + " ，" + ShopCodeEnum.SHOP_GOODS_NUM_NOT_ENOUGH.getMessage());
        }
        goods.setGoodsNumber(goods.getGoodsNumber() - quantity);
        return goodsMapper.updateById(goods);
    }

}
