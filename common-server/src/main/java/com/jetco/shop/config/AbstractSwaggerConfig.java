package com.jetco.shop.config;

import com.jetco.shop.bean.SwaggerProperties;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * <p>
 * Swagger基础义配置
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-10
 */
public abstract class AbstractSwaggerConfig {

    /**
     * 自定义Swagger配置
     * @return
     */
    public abstract SwaggerProperties swaggerProperties();

    @Bean
    public Docket createRestApi() {
        SwaggerProperties swaggerProperties = swaggerProperties();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(swaggerProperties))
                // 分组名称
                .groupName("default")
                .select()
                // 对类上注解@RestController进行监控
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                // 不显示错误的接口地址
                .paths(PathSelectors.any())
                // 错误路径不监控
                .paths(PathSelectors.regex("/error.*").negate())
                .build();
        if (swaggerProperties.isEnableSecurity()) {
            docket.securityContexts(CollectionUtils.newArrayList(securityContext()))
                    .securitySchemes(CollectionUtils.<SecurityScheme>newArrayList(apiKey()));
        }
        return docket;
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description("<div style='font-size:16px;color:red;'>" + swaggerProperties.getDescription() + "</div>")
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/*/.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return CollectionUtils.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }

}
