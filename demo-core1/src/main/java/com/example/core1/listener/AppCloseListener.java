package com.example.core1.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

@Slf4j
public class AppCloseListener implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private CuratorFramework curatorFramework;
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
//        closeCurator();
        log.info("===============程序停止");
    }

    private void closeCurator() {
        if(curatorFramework != null){
            curatorFramework.close();
        }
    }
}
