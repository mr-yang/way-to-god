package com.example.core1.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author tianxiaoyang
 * @date 2021-08-09 09:54
 * @describe 自定义灰度负载均衡策略，暂时没有使用
 */
@Slf4j
public class GrayscaleRibbonRule extends AbstractLoadBalancerRule {

    private AbstractLoadBalancerRule ribbonRule;

    public GrayscaleRibbonRule() {
        //这里判断不走灰度的情况下用哪种负载均衡策略
        ribbonRule = new RoundRobinRule();
    }
    public GrayscaleRibbonRule(ILoadBalancer lb) {
        setLoadBalancer(lb);
    }
    @Override
    public void setLoadBalancer(ILoadBalancer lb){
        super.setLoadBalancer(lb);
        ribbonRule.setLoadBalancer(lb);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        log.info("*******GrayscaleRibbonRule------initWithNiwsConfig:{}",iClientConfig);
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    public Server choose(ILoadBalancer lb, Object key) {
        List<Server> reachableServers = lb.getReachableServers();
        List<Server> allServers = lb.getAllServers();
        int upCount = reachableServers.size();
        int serverCount = allServers.size();

        if ((upCount == 0) || (serverCount == 0)) {
            log.warn("没有可用的服务，走默认的负载均衡策略，由默认的负载均衡器去处理: " + lb);
            return ribbonRule.choose(key);
        }
        //判断是否是灰度应用


        //如果是走灰度逻辑
        //如果不是走以前的负载均衡策略
        return ribbonRule.choose(key);
    }
}
