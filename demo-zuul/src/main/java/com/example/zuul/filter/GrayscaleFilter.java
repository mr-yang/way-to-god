package com.example.zuul.filter;

import com.example.zuul.config.GrayscaleBean;
import com.example.zuul.config.GrayscaleConfig;
import com.example.zuul.handle.GrayscaleHandle;
import com.example.zuul.util.HttpServletRequestUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author tianxiaoyang
 * @date 2021-08-04 15:22
 * @describe 灰度发布的Filter
 * 测试地址：http://localhost:10001/demo-core/testGrayscale
 * 测试报文：{"grayscaleId":"666"}，grayscaleId为666走core2服务，其他值都走core1服务
 */
@Slf4j
@Component
public class GrayscaleFilter extends ZuulFilter {

    @Autowired
    private GrayscaleConfig config;

    @Autowired
    private ApplicationContext applicationContext;

    //filterType ：返回字符串，代表过滤器的类型。包含以下4种：
    //pre ：请求在被路由之前执⾏
    //routing ：在路由请求时调⽤
    //post ：在routing和errror过滤器之后调⽤
    //error ：处理请求时发⽣错误调⽤
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    //通过返回的int值来定义过滤器的执⾏顺序，数字越⼩优先级越⾼。
    @Override
    public int filterOrder() {
        return 0;
    }
    /**
     * @Description: 返回⼀个 Boolean 值，判断该过滤器是否需要执⾏。返回true执⾏，返回false 不执⾏。
     * @Author: tianxiaoyang
     * @Date: 2021/8/4 4:07 下午
     **/
    @Override
    public boolean shouldFilter() {
        //获取应用名称
        String serverId = HttpServletRequestUtil.getServerId(RequestContext.getCurrentContext().getRequest());
        if(StringUtils.isNotBlank(serverId)){
            //判断是否需要灰度发布
            return config.isGrayscale(serverId);
        }
        return false;
    }

    //过滤器的具体业务逻辑。
    @Override
    public Object run() throws ZuulException {
        String serverId = HttpServletRequestUtil.getServerId(RequestContext.getCurrentContext().getRequest());
        GrayscaleBean grayscale = config.getGrayscale(serverId);
        if(grayscale == null){
            return null;
        }
        GrayscaleHandle grayscaleHandle = applicationContext.getBean(grayscale.getHandleName(), GrayscaleHandle.class);
        if(grayscaleHandle == null){
            return null;
        }
        grayscaleHandle.grayscaleHandle(grayscale.getGrayscaleKey(),RequestContext.getCurrentContext().getRequest());
        return null;
    }
}
