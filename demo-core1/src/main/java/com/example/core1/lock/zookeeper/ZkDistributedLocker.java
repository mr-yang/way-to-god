package com.example.core1.lock.zookeeper;

import com.example.core1.lock.DistributedLocker;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: tianxiaoyang
 * @Date: 2020/8/6 15:02
 * @Description: 基于Curator实现的zookeeper方式的分布式锁
 */
@Slf4j
@Component("zkDistributedLocker")
public class ZkDistributedLocker implements DistributedLocker<InterProcessLock> {

    @Resource
    private CuratorFramework curatorFramework;

    @Override
    public InterProcessLock lock(String lockKey) {
        InterProcessMutex interProcessLock =null;
        try {
            interProcessLock = new InterProcessMutex(curatorFramework,
                    lockKey);
            interProcessLock.acquire();
        } catch (Exception e) {
            log.error(lockKey+" zk获取分布式锁异常",e);
            throw new RuntimeException("zk获取分布式锁异常");
        }
        return interProcessLock;
    }

    @Override
    public InterProcessLock lock(String lockKey, int leaseTime) {
        log.info("zk锁不需要设置续约时间，因为zk用的临时节点，断开连接自动释放锁");
        return lock(lockKey);
    }

    @Override
    public InterProcessLock lock(String lockKey, TimeUnit unit, int timeout) {
        InterProcessMutex interProcessLock =null;
        try {
            interProcessLock = new InterProcessMutex(curatorFramework,
                    lockKey);
            interProcessLock.acquire(timeout,unit);
        } catch (Exception e) {
            log.error(lockKey+" zk获取分布式锁异常",e);
        }
        return interProcessLock;
    }
    @Override
    public boolean tryLock(String lockKey) {
        try {
            InterProcessMutex interProcessLock = new InterProcessMutex(curatorFramework,
                    lockKey);
            return interProcessLock.acquire(-1,TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error(lockKey+" zk获取分布式锁异常",e);
        }
        return false;
    }
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        try {
            InterProcessMutex interProcessLock = new InterProcessMutex(curatorFramework,
                    lockKey);
            return interProcessLock.acquire(waitTime,unit);
        } catch (Exception e) {
            log.error(lockKey+" zk获取分布式锁异常",e);
        }
        return false;
    }

    @Override
    public void unlock(String lockKey) {
        throw  new RuntimeException("zk实现的分布式锁不支持此方式释放锁");
    }

    @Override
    public void unlock(InterProcessLock lock) {
        if(lock == null){
            return;
        }
        try {
            lock.release();
        } catch (Exception e) {
            log.error("zk释放分布式锁异常",e);
        }
    }

}
