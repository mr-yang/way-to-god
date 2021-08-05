package com.example.core2.bean;

import lombok.Data;

/**
 * @author tianxiaoyang
 * @date 2021-08-04 17:05
 * @describe
 */
@Data
public class TestGrayscale {

    private String grayscaleId;

    public String getGrayscaleId() {
        return grayscaleId;
    }

    public void setGrayscaleId(String grayscaleId) {
        this.grayscaleId = grayscaleId;
    }
}
