package com.example.core1.aspect;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tianxiaoyang
 * @date 2021-08-10 11:40
 * @describe 应用到应用灰度的切面，用来修改想要的负载均衡逻辑
 */
@Aspect
@Component
@Slf4j
public class GrayscaleAspectPointcut {

    /**
     * 格式：execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?
     * name-pattern(param-pattern) throws-pattern?)
     */
    @Pointcut("execution(* com.example.core1.controller.GrayscaleController.*(..))")
    public void grayscaleController() {
    }


    @Around("grayscaleController()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] pjpArgs = pjp.getArgs();
        if (pjpArgs == null || pjpArgs.length ==0){
            return pjp.proceed();
        }
        Object pjpArg = pjpArgs[0];
        if (filterParameter(pjpArg)) {
            return pjp.proceed();
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> objectMap = mapper.readValue(mapper.writeValueAsString(pjpArg), new TypeReference<HashMap<String, Object>>() {
        });
        Object grayscaleIdObj = objectMap.get("grayscaleId");
        if(grayscaleIdObj == null || !(grayscaleIdObj instanceof String)){
            return pjp.proceed();
        }
        String grayscaleId = (String) grayscaleIdObj;
        if("666".equals(grayscaleId)){
            RibbonFilterContextHolder.getCurrentContext().add("version","grayscale");
        }else{
            RibbonFilterContextHolder.getCurrentContext().add("version","release");
        }
        return pjp.proceed(pjpArgs);
    }

    protected boolean filterParameter(Object arg) {
        return (arg == null || arg instanceof String || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse);
    }
}
