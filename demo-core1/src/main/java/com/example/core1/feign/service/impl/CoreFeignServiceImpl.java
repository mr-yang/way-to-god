package com.example.core1.feign.service.impl;

import com.example.core1.bean.TestGrayscale;
import com.example.core1.feign.service.CoreFeignService;
import org.springframework.stereotype.Service;

/**
 * @author tianxiaoyang
 * @date 2021-08-09 13:51
 * @describe
 */
@Service
public class CoreFeignServiceImpl implements CoreFeignService {
    @Override
    public String testAppGrayscale(TestGrayscale testGrayscale) {
        return "核心出现异常，进入断路器";
    }
}
