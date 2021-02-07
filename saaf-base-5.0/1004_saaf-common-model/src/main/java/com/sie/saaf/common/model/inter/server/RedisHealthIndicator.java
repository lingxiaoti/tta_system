package com.sie.saaf.common.model.inter.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/**
 * 由于在Spring Boot项目中引用了Redis模块，所以Spring Boot Actuator会对其进行健康检查，但是由于采用了其他配置方式，导致redis的连接检查没有通过。这样就会导致了Consul或Eureka的HealthCheck认为该服务是DOWN状态
 * 这里覆盖原来的redis健康检查实现
 *
 * @author huangtao
 */
@Component("redisHealthIndicator")
public class RedisHealthIndicator implements HealthIndicator {

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public Health health() {
        jedisCluster.getClusterNodes();
        return Health.up().build();
    }
}
