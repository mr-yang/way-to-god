package com.example.core1.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author tianxiaoyang
 * @date 2021-08-10 10:37
 * @describe 测试相关配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "test")
public class TestConfig {

    private GrayscaleConfig grayscale;
}
