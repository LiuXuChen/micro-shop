package com.jetco.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jetco.shop.entity.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * account
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-14
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
