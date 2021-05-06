package com.jetco.shop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 商品类
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-13
 */
@TableName("t_goods")
public class Goods implements Serializable {

    @TableId
    @TableField("goods_id")
    private Long goodsId;

    @TableField("goods_name")
    private String goodsName;

    @TableField("goods_number")
    private Long goodsNumber;

    @TableField("goods_price")
    private BigDecimal goodsPrice;

    @TableField("goods_desc")
    private String goodsDesc;

    @TableField("add_time")
    private LocalDate addTime;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Long getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Long goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc == null ? null : goodsDesc.trim();
    }

    public LocalDate getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDate addTime) {
        this.addTime = addTime;
    }
}
