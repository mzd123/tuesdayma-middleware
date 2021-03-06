# java中常用的一些中间件

## redis
版本：spring-boot-starter-data-redis 2.1.5.RELEASE

包含内容：
   1. list、set、zset、hash数据类型的基本操作
   2. scan 对 key、list、set、zset、hash的基本操作
   3. bitmap 的基本操作
   4. redis发布订阅模式
   5. 基于redis+threadPoolExecutor实现延迟队列,redis持久化延迟任务，threadPoolExecutor线程池来调度任务

## elasticsearch
版本：elasticsearch 7.4.2(支持集群)

包含内容：
   1. 判断索引是否存在、创建索引、删除索引
   2. 向索引插入数据、删除数据、更新数据、查询数据
   
## nacos
版本：nacos 1.1.3(支持集群)、nacos-config-spring-boot-starter(0.2.4)

包含内容：
   1. 作为项目配置中心，向nacos服务端拉取配置文件，业务系统可以实时更新，该功能和spring-cloud-config、Apollo差不多
       支持的格式有：properties、json
   2. 作为前端的index.html配置页面
   3. 作为dubbo服务的注册中心
 
## sharding-jdbc
版本：sharding-jdbc-spring-boot-starter(4.0.0-RC2),mysql 5.7

包含内容：
   1. 分表分库
   2. 读写分离
   3. 给数据库字段的值自动做加解密

## rocketmq
版本：rocketmq-spring-boot-starter(2.1.1)

包含内容：
   1. 发送简单消息(同步消息、异步消息和单向消息)
   2. 发送延迟消息(同步消息、异步消息)

## 钉钉群通知
包含内容：
   1. 向钉钉群发送文本信息、连接、markdown形式的信息