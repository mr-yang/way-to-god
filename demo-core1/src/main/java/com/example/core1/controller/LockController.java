package com.example.core1.controller;

import com.example.core1.bean.TestLockBean;
import com.example.core1.lock.LockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author tianxiaoyang
 * @date 2021-08-11 14:53
 * @describe 用来测试分布式锁
 */
@Slf4j
@RestController
public class LockController {


    @Resource
    private LockService lockService;
    @PostMapping("/testRedisLock")
    public String testRedisLock(@RequestBody @Valid TestLockBean testLockBean){
        lockService.testRedisLockCount(testLockBean);
        return "Redis分布式锁，执行成功。";
    }

    @PostMapping("/testZookeeperLock")
    public String testZookeeperLock(@RequestBody @Valid TestLockBean testLockBean){
        lockService.testZookeeperLockCount(testLockBean);
        return "Zookeeper分布式锁，执行成功。";
    }
}
