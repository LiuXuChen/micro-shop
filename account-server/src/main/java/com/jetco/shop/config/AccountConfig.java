package com.jetco.shop.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 账户信息配置
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-09
 */
@Getter
@RefreshScope
@Configuration
public class AccountConfig {

    @Value("${account.name}")
    private String name;

    @Value("${account.age}")
    private int age;

}
