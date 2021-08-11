package com.example.core1.lock;

/**
 * @author tianxiaoyang
 * @date 2021-08-11 16:19
 * @describe
 */
public enum LockEnum {

    NO_LOCK(0, "无锁"),
    REDIS_BIO_LOCK(1, "Redis阻塞锁"),
    REDIS_NIO_LOCK(2, "Redis非阻塞锁，快速失败");

    private int lockType;
    private String msg;

    LockEnum(int lockType, String msg) {
        this.lockType = lockType;
        this.msg = msg;
    }
}
