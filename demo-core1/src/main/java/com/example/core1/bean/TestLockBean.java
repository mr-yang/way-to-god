package com.example.core1.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tianxiaoyang
 * @date 2021-08-11 15:26
 * @describe
 */
@Data
public class TestLockBean {
    @NotNull(message = "userId 不能为空，打印谁获取到锁需要")
    private String userId;

    //是否快速失败，也就是拿不到锁直接响应失败
    private boolean isFastFail;
}
