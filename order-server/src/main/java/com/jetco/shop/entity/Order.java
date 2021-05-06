package com.jetco.shop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 订单类
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-13
 */
@TableName("t_order")
public class Order implements Serializable {

    @TableId
    @TableField("order_id")
    private Long orderId;

    @TableField("account_id")
    private Long accountId;

    @TableField("order_status")
    private Integer orderStatus;

    private String address;

    @TableField("goods_id")
    private Long goodsId;

    @TableField("goods_number")
    private Integer goodsNumber;

    @TableField("goods_price")
    private BigDecimal goodsPrice;

    @TableField("goods_amount")
    private Integer goodsAmount;

    @TableField("coupon_id")
    private Long couponId;

    public Order(Long accountId, Long goodsId, Long couponId) {
        this.accountId = accountId;
        this.goodsId = goodsId;
        this.couponId = couponId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

}
