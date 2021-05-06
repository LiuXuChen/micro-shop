package com.jetco.shop.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jetco.shop.bean.Result;
import com.jetco.shop.entity.Order;
import com.jetco.shop.enums.ResultCodeEnum;
import com.jetco.shop.enums.ShopCodeEnum;
import com.jetco.shop.exception.SentinelException;
import com.jetco.shop.exception.WebException;
import com.jetco.shop.feignclient.AccountServiceFeignClient;
import com.jetco.shop.feignclient.GoodsServiceFeignClient;
import com.jetco.shop.mapper.OrderMapper;
import io.seata.common.util.IdWorker;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 订单服务类
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-11
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private AccountServiceFeignClient accountServiceFeignClient;

    @Resource
    private GoodsServiceFeignClient goodsServiceFeignClient;

    /**
     * 创建订单
     *
     *  @SentinelResource 注解用于声明Sentinel资源点，只有声明了资源点，Sentinel才能实施限流熔断等保护措施
     *  createOrder：自定义资源点名称
     *  blockHandler：自定义资源点的异常处理
     * @return
     */
    public Order createOrder(Long accountId, Long goodsId, Long couponId) {
        Order order = new Order(accountId, goodsId, couponId);
        // 设置订单状态为不可见
        order.setOrderStatus(ShopCodeEnum.SHOP_ORDER_NO_CONFIRM.getCode());
        // 设置订单ID
        long orderId = IdWorker.getInstance().nextId();
        order.setOrderId(orderId);
        order.setAddress("江西省南昌市红谷滩新区我家");
        order.setGoodsNumber(1);
        order.setGoodsPrice(new BigDecimal(1000));
        order.setGoodsAmount(1000);
        int count = orderMapper.insert(order);
        if(count > 0) {
            return order;
        }
        throw new WebException(ShopCodeEnum.SHOP_ORDER_ERROR.getMessage());
    }

    @SentinelResource(value = "createOrder", blockHandler = "createOrderBlockHandler")
    @GlobalTransactional(name = "seata-group-tx-shop", rollbackFor = {Exception.class})
    public int shopping(Long accountId, Long goodsId, Long couponId, Long quantity) {
        // 创建订单
        Order order = this.createOrder(accountId, goodsId, couponId);

        // 增加账号积分 + 扣减账户金额
        Result<String> accountResult = accountServiceFeignClient.reduceMoney(accountId, order.getGoodsPrice(), 1);
        if(!accountResult.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            throw new WebException(ShopCodeEnum.SHOP_ORDER_ERROR.getMessage());
        }
        // 扣减商品数量
        Result<String> stockResult = goodsServiceFeignClient.reduceStock(goodsId, quantity);
        if(!stockResult.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            throw new WebException(ShopCodeEnum.SHOP_ORDER_ERROR.getMessage());
        }

        // 修改订单状态
        order.setOrderStatus(ShopCodeEnum.SHOP_ORDER_CONFIRM.getCode());
        return orderMapper.updateById(order);
    }


    public void createOrderBlockHandler(BlockException e) {
        int code = ResultCodeEnum.UNDEFINE.getCode();
        String msg = ResultCodeEnum.UNDEFINE.getMessage();
        if(e instanceof FlowException) {
            code = ResultCodeEnum.INTERFACE_HAS_BEEN_THROTTLED.getCode();
            msg = ResultCodeEnum.INTERFACE_HAS_BEEN_THROTTLED.getMessage();
        } else if(e instanceof DegradeException) {
            code = ResultCodeEnum.INTERFACE_HAS_BEEN_BLOWN.getCode();
            msg = ResultCodeEnum.INTERFACE_HAS_BEEN_BLOWN.getMessage();
        } else if (e instanceof ParamFlowException) {
            code = ResultCodeEnum.HOTSPOT_PARAMETERS_HAVE_BEEN_RESTRICTED.getCode();
            msg = ResultCodeEnum.HOTSPOT_PARAMETERS_HAVE_BEEN_RESTRICTED.getMessage();
        } else if (e instanceof SystemBlockException) {
            code = ResultCodeEnum.SYSTEM_RULES_DO_NOT_MEET_THE_REQUIREMENTS.getCode();
            msg = ResultCodeEnum.SYSTEM_RULES_DO_NOT_MEET_THE_REQUIREMENTS.getMessage();
        } else if (e instanceof AuthorityException) {
            code = ResultCodeEnum.AUTHORIZATION_RULES_FAIL.getCode();
            msg = ResultCodeEnum.AUTHORIZATION_RULES_FAIL.getMessage();
        }
        throw new SentinelException(code, msg);
    }

}
