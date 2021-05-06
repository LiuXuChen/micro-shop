package com.jetco.shop;

import okhttp3.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.StopWatch;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 订单服务启动类
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-10
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication {

    private static final Logger logger = LoggerFactory.getLogger(OrderApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ConfigurableApplicationContext application = SpringApplication.run(OrderApplication.class, args);
        stopWatch.stop();
        Environment env = application.getEnvironment();
        logger.info("\n------------------------------------------------------------------------------\n\t\t\t" +
                        "Application: '{}'  Started Successfully!\n\t\t\t" +
                        "Local: \t\thttp://localhost:{}\n\t\t\t" +
                        "External: \thttp://{}:{}\n\t\t\t"+
                        "Started Application Cost: \t{} ms \n"+
                        "------------------------------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                stopWatch.getTotalTimeMillis());
    }

    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        return new okhttp3.OkHttpClient.Builder()
                // 连接超时时间
                .connectTimeout(10, TimeUnit.SECONDS)
                //读取超时时间
                .readTimeout(10, TimeUnit.SECONDS)
                // 写入超时时间
                .writeTimeout(10, TimeUnit.SECONDS)
                // 设置连接池
                .connectionPool(new ConnectionPool())
                .build();
    }

}
