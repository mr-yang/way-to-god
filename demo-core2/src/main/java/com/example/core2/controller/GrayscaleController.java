package com.example.core2.controller;

import com.example.core2.bean.TestGrayscale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tianxiaoyang
 * @date 2021-08-04 17:03
 * @describe 测试灰度发布的
 */
@Slf4j
@RestController
public class GrayscaleController {


    @PostMapping("/testGrayscale")
    public String testGrayscale(@RequestBody TestGrayscale testGrayscale){
        log.info("我是灰度core2，收到的请求数据为 {}",testGrayscale);
        return "我是灰度核心2";
    }
}
