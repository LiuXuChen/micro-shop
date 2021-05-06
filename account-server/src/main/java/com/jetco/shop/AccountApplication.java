package com.jetco.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StopWatch;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>
 * 账户服务启动类
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-09
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AccountApplication {

    private static final Logger logger = LoggerFactory.getLogger(AccountApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ConfigurableApplicationContext application = SpringApplication.run(AccountApplication.class, args);
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
}
