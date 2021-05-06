package com.jetco.shop.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jetco.shop.config.AccountConfig;
import com.jetco.shop.entity.Account;
import com.jetco.shop.exception.NotFoundException;
import com.jetco.shop.exception.ParameterException;
import com.jetco.shop.mapper.AccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * <p>
 * 账户服务
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-11
 */
@Service
public class AccountService extends ServiceImpl<AccountMapper, Account> {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private AccountConfig accountConfig;

    public String getAccountInfo() {
        return "name: " + accountConfig.getName() + ",age: " + accountConfig.getAge();
    }

    /**
     * 扣减账户金额
     * @param accountId
     * @param money
     * @param score
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int reduceMoney(Long accountId, BigDecimal money, Integer score) {
        Account account = Optional.ofNullable(accountMapper.selectById(accountId)).orElseThrow(()-> new NotFoundException(String.format("根据账号ID：%s没找到对应的账号", accountId)));
        if(account.getMoney().compareTo(money) < 0) {
            throw new ParameterException("你的金额不足，请充值！");
        }
        account.setMoney(account.getMoney().subtract(money));
        account.setScore(account.getScore() + score);
        return accountMapper.updateById(account);
    }
}
