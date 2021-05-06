package com.jetco.shop.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 商品配置信息
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-09
 */
@Getter
@RefreshScope
@Configuration
public class GoodsConfig {

    @Value("${goods.name}")
    private String name;

    @Value("${goods.desc}")
    private String desc;

}
