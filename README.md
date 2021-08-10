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