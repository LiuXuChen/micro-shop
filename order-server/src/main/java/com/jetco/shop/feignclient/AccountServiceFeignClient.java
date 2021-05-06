package com.jetco.shop.feignclient;

import com.jetco.shop.bean.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * <p>
 * 账户服务客户端
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-14
 */
@FeignClient(name = "account-server")
public interface AccountServiceFeignClient {

    /**
     * 扣减账户金额 + 增加账户积分
     * @param accountId
     * @param money
     * @param score
     * @return
     */
    @PostMapping("/account/reduceMoney")
    Result<String> reduceMoney(@RequestParam("accountId") Long accountId, @RequestParam("money") BigDecimal money, @RequestParam("score") Integer score);

}
