package com.example.core1.lock;

import com.example.core1.bean.TestLockBean;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tianxiaoyang
 * @date 2021-08-11 15:22
 * @describe
 */
@Slf4j
@Service
public class LockService {
    @Resource
    private DistributedLocker locker;
    private final String lockKey = "redis-lock-test";
    private int count = 0;
    /**
     * 加锁测试
     * @param testLockBean
     */
    public void testLockCount(TestLockBean testLockBean) {
        RLock lock = null;
        try {
            String name = testLockBean.getUserId();
            log.info( name + " 线程进入 run 方法");
            lock = this.locker.lock(lockKey);
            log.info(name + " 线程获取到了锁");
            //执行临界区代码
            addCont();
        } catch (Exception e) {
            log.error("获取锁异常",e);
        } finally {
            locker.unlock(lock);
            String name = Thread.currentThread().getName();
            log.info(name + "线程释放了锁");
        }
    }

    private void addCont() {
        count++;
        log.info("count的值为 {}",count);
    }
}
