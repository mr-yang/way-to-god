package com.example.core1.feign;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @Description: Feign默认配置类
* @Author: xiaoyang
* @Date: 2020-03-14 12:15
*/
@Configuration
public class FeignDefaultConfig {
	
    /**
     * @Description：feign关闭重试
     */
    @Bean
    public Retryer retryer() {
    	return Retryer.NEVER_RETRY;
    }
    
    
    /**
     * @Description：
     * NONE，无记录（DEFAULT）。
     * BASIC，只记录请求方法和URL以及响应状态代码和执行时间。
     * HEADERS，记录基本信息以及请求和响应头。
     * FULL，记录请求和响应的头文件，正文和元数据
     *
     * @return
     * Logger.Level 
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.HEADERS;
    }
}
