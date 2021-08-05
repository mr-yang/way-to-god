package com.example.zuul.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tianxiaoyang
 * @date 2021-08-05 14:39
 * @describe
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrayscaleBean {

    //灰度字段
    private String grayscaleKey;
    //灰度处理器的名字
    private String handleName;

}
