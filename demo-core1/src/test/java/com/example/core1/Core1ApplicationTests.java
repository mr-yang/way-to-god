package com.example.core1;

import com.example.core1.lock.DistributedLocker;
import com.example.core1.lock.LockEnum;
import com.example.core1.lock.LockRunnable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@Slf4j
@SpringBootTest
class Core1ApplicationTests {
    private static int threadNum = 2001;
    @Resource
    private DistributedLocker redisDistributedLocker;

    @Test
    void testLock() throws Exception {
        // 计数器
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < threadNum; i++) {
            LockRunnable myRunnable = new LockRunnable(redisDistributedLocker, countDownLatch, LockEnum.REDIS_NIO_LOCK);
            Thread myThread = new Thread(myRunnable, "线程" + i);
            myThread.start();
        }
        countDownLatch.countDown();
        System.in.read();
//        Thread.sleep(8000);
        log.info("count 的值为：{}", LockRunnable.count);
    }

}
