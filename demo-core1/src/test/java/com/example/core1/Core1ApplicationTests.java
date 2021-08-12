package com.example.core1;

import com.example.core1.lock.DistributedLocker;
import com.example.core1.lock.LockEnum;
import com.example.core1.lock.redis.LockRedisRunnable;
import com.example.core1.lock.zookeeper.LockZookeeperRunnable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@Slf4j
@SpringBootTest
class Core1ApplicationTests {
    private static int threadNum = 111;
    @Resource
    private DistributedLocker redisDistributedLocker;
    @Resource
    private DistributedLocker zkDistributedLocker;
    @Test
    void testRedisLock() throws Exception {
        // 计数器
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < threadNum; i++) {
            LockRedisRunnable myRunnable = new LockRedisRunnable(redisDistributedLocker, countDownLatch, LockEnum.REDIS_NIO_LOCK);
            Thread myThread = new Thread(myRunnable, "线程" + i);
            myThread.start();
        }
        countDownLatch.countDown();
        System.in.read();
//        Thread.sleep(8000);
        log.info("count 的值为：{}", LockRedisRunnable.count);
    }

    @Test
    void testZookeeperLock() throws Exception {
        // 计数器
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < threadNum; i++) {
            LockZookeeperRunnable myRunnable = new LockZookeeperRunnable(zkDistributedLocker, countDownLatch, LockEnum.ZK_BIO_LOCK);
            Thread myThread = new Thread(myRunnable, "线程" + i);
            myThread.start();
        }
        countDownLatch.countDown();
        System.in.read();
//        Thread.sleep(8000);
        log.info("count 的值为：{}", LockRedisRunnable.count);
    }

}
