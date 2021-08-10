package com.example.core1.feign.service;

import com.example.core1.bean.TestGrayscale;
import com.example.core1.feign.FeignDefaultConfig;
import com.example.core1.feign.factory.CoreFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author tianxiaoyang
 * @date 2021-08-09 13:44
 * @describe
 */
@FeignClient(name = "demo-test-grayscale", configuration = FeignDefaultConfig.class,fallbackFactory = CoreFallBackFactory.class)
public interface CoreFeignService {

    @PostMapping("/testAppGrayscale")
    String testAppGrayscale(@RequestBody TestGrayscale testGrayscale);
}
