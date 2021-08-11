package com.example.core1;

import com.example.core1.lock.DistributedLocker;
import com.example.core1.lock.LockRunnable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Slf4j
@SpringBootTest
class Core1ApplicationTests {
    private static int threadNum = 100;
    @Resource
    private DistributedLocker redisDistributedLocker;
    @Test
    void testLock() throws IOException {
        // 计数器
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < threadNum; i++) {
            LockRunnable myRunnable = new LockRunnable(redisDistributedLocker,countDownLatch);
            Thread myThread = new Thread(myRunnable);
            myThread.setName("线程" + i);
            myThread.start();
        }
        countDownLatch.countDown();
        System.in.read();
    }

}
