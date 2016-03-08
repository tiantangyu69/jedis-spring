#Jedis Spring客户端

##PooledRedisFactoryBean
###定义RedisClient接口
###使用javassist动态代理JedisPool

##PooledSharedJedisFactoryBean
###定义SharedRedisClient接口
###使用javassist动态代理SharedJedisPool

##MasterSlaveFactoryBean
###定义MasterSlaveRedisClient接口
###使用javassist动态代理MasterSlaveRedisClient

##PooledRedis和MasterSlaveRedis增加对事务的支持
###通过回调Jedis原生API执行事务
