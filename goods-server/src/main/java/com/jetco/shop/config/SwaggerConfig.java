package com.jetco.shop.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.jetco.shop.bean.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * <p>
 * swagger配置
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-10
 */
@Configuration
@EnableKnife4j
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig extends AbstractSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .title("商品服务")
                .description("商品服务接口文档")
                .contactName("lhw")
                .version("1.0.0")
                .enableSecurity(Boolean.TRUE)
                .build();
    }
}
