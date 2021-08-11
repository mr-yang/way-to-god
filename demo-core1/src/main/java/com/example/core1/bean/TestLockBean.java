package com.example.core1.bean;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author tianxiaoyang
 * @date 2021-08-11 15:26
 * @describe
 */
@Data
public class TestLockBean {
    @NotEmpty(message = "userId 不能为空，打印谁获取到锁需要")
    private String userId;
}
