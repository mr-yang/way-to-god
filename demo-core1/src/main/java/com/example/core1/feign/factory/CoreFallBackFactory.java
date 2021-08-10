package com.example.core1.feign.factory;

import com.example.core1.feign.service.CoreFeignService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tianxiaoyang
 * @date 2021-08-09 13:42
 * @describe 核心进断路器工厂
 */
@Slf4j
@Component
public class CoreFallBackFactory implements FallbackFactory<CoreFeignService> {

    @Resource
    private CoreFeignService coreFeignService;

    @Override
    public CoreFeignService create(Throwable throwable) {
        log.error("核心服务异常", throwable);
        return coreFeignService;
    }
}