package com.jetco.shop.config;

import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * swagger资源配置
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-10
 */
@Component
@Primary
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    /**
     * 路由加载器
     */
    private final RouteLocator routeLocator;

    /**
     * 网关属性
     */
    private final GatewayProperties gatewayProperties;

    public SwaggerResourceConfig(final RouteLocator routeLocator, final GatewayProperties gatewayProperties) {
        this.routeLocator = routeLocator;
        this.gatewayProperties = gatewayProperties;
    }

    @Override
    public List<SwaggerResource> get() {
        // 接口资源列表
        List<SwaggerResource> resources = new ArrayList<>();
        // 服务名称列表
        List<String> routes = new ArrayList<>();
        // 获取所有可用的应用名称
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        // 去重，多负载服务只添加一次
        gatewayProperties.getRoutes().stream()
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(route -> route.getPredicates().stream()
                .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                .forEach(predicateDefinition -> resources.add(swaggerResource(route.getId(),
                        predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                .replace("**", "v2/api-docs")))));

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
