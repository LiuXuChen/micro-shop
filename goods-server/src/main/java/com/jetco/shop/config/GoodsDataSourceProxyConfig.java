package com.jetco.shop.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * <p>
 * DataSourceProxy数据源代理配置，要让Seata客户端在处理事务时自动生成反向SQL，必须配置
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-13
 */
@Configuration
public class GoodsDataSourceProxyConfig {

    /**
     * 创建Druid数据源
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    /**
     * 建立DataSource数据源代理
     * @param druidDataSource
     * @return
     */
    @Bean
    @Primary
    public DataSourceProxy dataSourceProxy(DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

}
