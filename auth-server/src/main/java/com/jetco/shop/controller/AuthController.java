package com.jetco.shop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 认证服务前端控制器
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-26
 */
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/test")
    public void test() {

    }
}
