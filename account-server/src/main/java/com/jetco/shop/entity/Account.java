package com.jetco.shop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 账户
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-13
 */
@ApiModel(value = "账号")
@TableName("t_account")
public class Account implements Serializable {

    @ApiModelProperty(value = "账号ID")
    @TableId
    @TableField("account_id")
    private Long accountId;

    @ApiModelProperty(value = "账号名")
    @TableField("account_name")
    private String accountName;

    @ApiModelProperty(value = "账号密码")
    private String password;

    @ApiModelProperty(value = "账号手机号")
    private String mobile;

    @ApiModelProperty(value = "账号积分")
    private Integer score;

    @ApiModelProperty(value = "账号注册时间")
    @TableField("reg_time")
    private LocalDate regTime;

    @ApiModelProperty(value = "账号金额")
    private BigDecimal money;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDate getRegTime() {
        return regTime;
    }

    public void setRegTime(LocalDate regTime) {
        this.regTime = regTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
