package com.example.zuul.handle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tianxiaoyang
 * @date 2021-08-05 14:22
 * @describe
 */
public interface GrayscaleHandle {

    void grayscaleHandle(String grayscale, HttpServletRequest httpServletRequest);
}
