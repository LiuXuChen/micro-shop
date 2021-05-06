package com.jetco.shop.controller;

import com.jetco.shop.bean.Result;
import com.jetco.shop.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 账户前端控制器
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-09
 */
@Api(tags = "账号服务API")
@RequestMapping("/account")
@RestController
public class AccountController {

    @Resource
    private AccountService accountService;

    @ApiOperation(value = "获取配置中的账号信息")
    @GetMapping("/getAccountInfo")
    public Result<String> getAccountInfo() {
        return Result.success(accountService.getAccountInfo());
    }

    @ApiOperation(value = "创建订单后扣减账号金额并增加账号积分")
    @PostMapping("/reduceMoney")
    public Result<String> reduceMoney(@RequestParam("accountId") Long accountId, @RequestParam("money") BigDecimal money, @RequestParam("score") Integer score) {
        int count = accountService.reduceMoney(accountId, money, score);
        if(count > 0) {
            return Result.success("金额扣减成功！");
        }
        return Result.failure("金额扣减失败！");
    }

}
