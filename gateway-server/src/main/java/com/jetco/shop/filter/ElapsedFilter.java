package com.jetco.shop.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 计时过滤器：
 * 任何从网关访问的请求，都要在日志中记录下从请求到响应退出的执行时间，
 * 通过这个时间可以收集分析哪些功能进行了慢处理，然后再以此进行优化。
 *
 * 全部过滤器必须要实现两个接口：GlobalFilter, Ordered
 *
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-10
 */
@Component
public class ElapsedFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(ElapsedFilter.class);

    /**
     * 起始时间属性名
     */
    private static final String ELAPSED_TIME_BEGIN = "elapsedTimeBegin";

    /**
     * 实现filter()方法记录处理时间
     * @param exchange 用于获取当前请求、响应相关的数据，以及设置过滤器间传递的上下文数据
     * @param chain Gateway过滤器链对象
     * @return Mono对于一个异步任务，因为Gateway是基于Netty Server异步处理的，Mono就代表异步处理完毕的情况
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Pre前置部分，在请求到达时，往ServerWebExchange上下文环境中放入一个属性elapsedTimeBegin，保存请求执行前的时间戳
        exchange.getAttributes().put(ELAPSED_TIME_BEGIN, System.currentTimeMillis());
        /*
         * chain.filter(exchange).then()对于Post后置处理部分，当响应产生后，
         * 记录结束与elapsedTimeBegin起始时间对比，获取RESTFUL API实际执行时间
         */
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    // 当前过滤器得到响应时，计算并打印时间
                    Long start = exchange.getAttribute(ELAPSED_TIME_BEGIN);
                    if (start != null) {
                        logger.info("远程访问的服务地址：{} , Gateway URI：{} , 处理时间为：{} ms",
                                exchange.getRequest().getRemoteAddress(),
                                exchange.getRequest().getPath(),
                                System.currentTimeMillis() - start);
                    }
                }));
    }

    /**
     * 设置为最高优先级，最先执行ElapsedFilter过滤器
     * Ordered.LOWEST_PRECEDENCE 代表设置为最低优先级
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
