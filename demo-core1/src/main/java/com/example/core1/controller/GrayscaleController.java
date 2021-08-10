package com.example.core1.controller;

import com.example.core1.bean.TestGrayscale;
import com.example.core1.config.TestConfig;
import com.example.core1.feign.service.CoreFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tianxiaoyang
 * @date 2021-08-04 17:03
 * @describe 测试灰度发布的
 */
@Slf4j
@RestController
public class GrayscaleController {

    @Resource
    private CoreFeignService coreFeignService;
    @Autowired
    private TestConfig testConfig;


    @PostMapping("/testGrayscale")
    public String testGrayscale(@RequestBody TestGrayscale testGrayscale){
        String tip = testConfig.getGrayscale().getTip();
        log.info(tip+"，收到的请求数据为 {}",testGrayscale);
        return tip;
    }

    @PostMapping("/testAppGrayscale")
    public String testAppGrayscale(@RequestBody TestGrayscale testGrayscale){
        return coreFeignService.testAppGrayscale(testGrayscale);
    }
}
