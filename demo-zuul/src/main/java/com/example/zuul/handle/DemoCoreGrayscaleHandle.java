package com.example.zuul.handle;

import com.example.zuul.util.HttpServletRequestUtil;
import com.netflix.zuul.context.RequestContext;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author tianxiaoyang
 * @date 2021-08-05 14:21
 * @describe
 */
@Slf4j
@Component("demoCoreGrayscaleHandle")
public class DemoCoreGrayscaleHandle implements GrayscaleHandle{

    @Override
    public void grayscaleHandle(String grayscale, HttpServletRequest httpServletRequest) {
        if(StringUtils.isBlank(grayscale)){
            return;
        }
        //取出请求的对象，判断路由规则，路由到响应的应用
        Map<String, Object> requestBody = HttpServletRequestUtil.getRequestBody(RequestContext.getCurrentContext().getRequest());
        if(requestBody == null){
            return;
        }
        Object grayscaleIdTem = requestBody.get(grayscale);
        if(grayscaleIdTem == null){
            return;
        }
        String grayscaleId = String.valueOf(grayscaleIdTem);
        RibbonFilterContextHolder.clearCurrentContext();
        if("666".equals(grayscaleId)){
            RibbonFilterContextHolder.getCurrentContext().add("version","grayscale");
        }else{
            RibbonFilterContextHolder.getCurrentContext().add("version","release");
        }
    }
}
