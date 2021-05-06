package com.jetco.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jetco.shop.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * order
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-13
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
