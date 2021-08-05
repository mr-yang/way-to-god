package com.example.zuul.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author tianxiaoyang
 * @date 2021-08-05 13:55
 * @describe
 */
@Slf4j
public class HttpServletRequestUtil {


    /**
     * @Description: 通过url获取应用id
     * Java网络编程-URI和URL
     * https://www.cnblogs.com/throwable/p/9740425.html
     * @Param: [request]
     * @return: java.lang.String
     * @Author: tianxiaoyang
     * @Date: 2021/8/5 2:02 下午
     **/
    public static String getServerId(HttpServletRequest request) {
        if (request == null) {
            log.error("HttpServletRequest 对象为空");
            return null;
        }
        try {
            StringBuffer requestURL = request.getRequestURL();
            URL url = new URL(requestURL.toString());
            String path = url.getPath();
            String[] split = path.split("/");
            if (split != null && split.length > 0) {
                return split[1];
            }
        } catch (MalformedURLException e) {
            log.error("获取url异常", e);
        }
        return null;
    }

    public static Map<String, Object> getRequestBody(HttpServletRequest request) {
        try {
            String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
            log.info("获取请求body {}", body);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(body, Map.class);
        } catch (IOException e) {
            log.error("获取请求body异常", e);
        }
        return null;
    }
}
