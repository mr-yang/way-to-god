### 一、网关到应用灰度测试

1、启动demo-eureka

2、启动demo-core1

​	2.1、启动的时候需要修改active为core1、core2这2个模拟正式核心集群

​	2.2、启动的时候需要修改active为grayscale1、grayscale2这2个模拟灰度核心集群

3、启动demo-zuul

​	灰度核心逻辑在com.example.zuul.filter.GrayscaleFilter类中

4、测试

​	测试地址：http://localhost:10001/demo-test-grayscale/testGrayscale

​	测试报文：{"grayscaleId":"666"}，grayscaleId为666走灰度服务，其他值都走正式服务

### 二、应用到应用灰度测试

1、启动demo-eureka

2、启动demo-core1

​	2.1、启动的时候需要修改active为core1、core2这2个模拟正式核心集群

​	2.2、启动的时候需要修改active为grayscale1、grayscale2这2个模拟灰度核心集群

3、直接启动demo-core1

​	灰度核心逻辑com.example.core1.aspect.GrayscaleAspectPointcut类中

4、启动demo-zuul

5、测试

​	测试地址：http://localhost:10001/demo-core/testAppGrayscale

​	测试报文：{"grayscaleId":"666"}，grayscaleId为666走灰度应用集群，其他值都走正式应用集群


### 三、Zuul动态路由


### 四、分布式锁测试

#### 1、基于Redis分布式锁，基于Redisson框架实现

1、测试

​	2.1、可以通过下面的测试方法做测试 com.example.core1.Core1ApplicationTests.testRedisLock

​	2.2、也可以调用 http://localhost:10010/testRedisLock 测试数据 {"userId":"123123"}
2、核心逻辑在 com.example.core1.lock.redis.RedisDistributedLocker 类中

#### 2、基于Zookeeper分布式锁，基于Curator框架实现

1、测试

​	2.1、可以通过下面的测试方法做测试 com.example.core1.Core1ApplicationTests.testZookeeperLock

​	2.2、也可以调用 http://localhost:10010/testZookeeperLock 测试数据 {"userId":"123123"}
    
2、核心逻辑在 com.example.core1.lock.zookeeper.ZkDistributedLocker 类中
