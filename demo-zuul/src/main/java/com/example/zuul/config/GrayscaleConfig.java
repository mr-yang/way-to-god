package com.example.zuul.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tianxiaoyang
 * @date 2021-08-04 16:09
 * @describe 灰度配置类，缓存一些灰度配置信息
 */
@Component
@Slf4j
public class GrayscaleConfig {

    //key是需要灰度的应用名称，value是灰度应用的
    private ConcurrentHashMap<String,GrayscaleBean> map = new ConcurrentHashMap<>();

    //灰度维度：应用、接口，只是为了演示简单，暂时应用维度灰度
    @PostConstruct
    private void initConfig(){
        map.put("demo-test-grayscale",new GrayscaleBean("grayscaleId","demoCoreGrayscaleHandle"));
    }

    //获取所有灰度信息
    public ConcurrentHashMap<String, GrayscaleBean> getMap() {
        return map;
    }

    //判断是否是需要灰度的应用
    public boolean isGrayscale(String key){
        if(StringUtils.isBlank(key)){
            return false;
        }
        return map.get(key) != null;
    }


    public GrayscaleBean getGrayscale(String key){
        return map.get(key);
    }

    //模拟添加灰度信息，后置改造
    public void addGrayscaleInfo(HashMap<String,GrayscaleBean> hashMap){
        map.putAll(hashMap);
    }
    //模拟移除灰度信息
    public void removeGrayscaleInfo(String key){
        map.remove(key);
    }
}
