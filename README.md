# tuesdayma-middleware
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
