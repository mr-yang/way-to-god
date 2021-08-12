package com.example.core1;

import com.example.core1.config.ZkCuratorConfig;
import com.example.core1.listener.AppCloseListener;
import com.example.core1.listener.AppStartListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
@EnableFeignClients
public class Core1Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Core1Application.class, args);
        applicationContext.addApplicationListener(new AppStartListener());
        applicationContext.addApplicationListener(new AppCloseListener());
    }


    @Bean
    public CuratorFramework curatorFramework(ZkCuratorConfig zkCuratorConfig) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(
                zkCuratorConfig.getConnectString(),
                zkCuratorConfig.getSessionTimeoutMs(),
                zkCuratorConfig.getConnectionTimeoutMs(),
                new RetryNTimes(zkCuratorConfig.getRetryCount(), zkCuratorConfig.getElapsedTimeMs()));
        curatorFramework.start();
        return curatorFramework;
    }

}
