package com.sie.saaf.demo.model.inter.server;

import com.yhg.redis.framework.JedisClusterCore;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * redis 基本操作
 * http://www.runoob.com/redis
 * @author yellowtao
 *
 */
public class RedisSampleServer extends JedisClusterCore{

    @Autowired
    private JedisCluster jedisCluster;


    /**
     * string 类型
     * 参考 http://www.runoob.com/redis/redis-strings.html
     */
    public void StringDemo() {
        //创建一个 key 并设置值
        jedisCluster.set("key", "val");
        //创建一个 key 并设置值, key在10秒后失效
        jedisCluster.setex("key", 10, "val");
        //指定的 key 不存在时，为 key 设置指定的值。key存在时返回0，key不存在时返回1
        Long setnxResult = jedisCluster.setnx("key", "val");
        //判断key是否存在
        jedisCluster.exists("key");
        //取值
        String getResult = jedisCluster.get("key");
        //模糊搜索----搜索的key中不能包含别的数据结构类型，不然会抛数据结构转换异常
        String cursor = "0";
        ScanParams scanParams = new ScanParams();
        scanParams.match("key前缀*");
        do {
            ScanResult<String> stringScanResult = jedisCluster.scan(cursor, scanParams);
            cursor = stringScanResult.getStringCursor();
            List<String> stringList = stringScanResult.getResult();
        } while (!"0".equals(cursor));

        //设置有效时间
        jedisCluster.expire("key",3600);
        jedisCluster.expireAt("key",System.currentTimeMillis()+3600000);
//        jedisCluster.set("","");
//        jedisCluster.expire("",3600);

        String keyv = jedisCluster.set("key","xx","nx","ex",3600);

    }


    /**
     * hash 结构
     * Redis hash 是一个string类型的field和value的映射表，hash特别适合用于存储对象。
     * Redis 中每个 hash 可以存储 2^32 - 1 键值对（40多亿）。
     *
     *  参考 http://www.runoob.com/redis/redis-hashes.html
     */
    public void hashDeamo() {
        //为哈希表中的字段赋值
        jedisCluster.hset("hashKey", "field", "val");
        //为哈希表中不存在的的字段赋值---设置成功，返回 1 。 如果给定字段已经存在且没有操作被执行，返回 0
        jedisCluster.hsetnx("hashKey", "field", "val");
        //查看哈希表的指定字段是否存在---如果哈希表含有给定字段，返回 1 。 如果哈希表不含有给定字段，或 key 不存在，返回 0
        jedisCluster.hexists("hashKey", "field");
        //同时将多个 field-value (域-值)对设置到哈希表 hashKey 中
        Map<String, String> map = new HashMap<>();
        map.put("field1", "val1");
        map.put("field2", "val2");
        jedisCluster.hmset("hashKey", map);
        //查看哈希表的指定字段是否存在
        jedisCluster.hexists("hashKey", "field");
        //获取存储在哈希表中指定字段的值。
        jedisCluster.hget("hashKey", "field");
        //获取在哈希表中指定 key 的所有字段和值-----数据量大时慎用，改用hscan
        jedisCluster.hgetAll("hashKey");
        //获取所有哈希表中的字段
        Set<String> hkeys = jedisCluster.hkeys("hashKey");
        //获取哈希表中所有值
        List<String> haval = jedisCluster.hvals("hashKey");
        //获取哈希表中字段的数量
        jedisCluster.hlen("hashKey");
        // 搜索哈希表
        ScanParams scanParams = new ScanParams();
        scanParams.match("field前缀*");
        String cursor = "0";
        do {
            // 可以不指定查询参数，遍历所有  jedisCluster.hscan("hashKey", cursor)
            ScanResult<Map.Entry<String, String>> stringScanResult = jedisCluster.hscan("hashKey", cursor, scanParams);
            cursor = stringScanResult.getStringCursor();
            List<Map.Entry<String, String>> entries = stringScanResult.getResult();
        } while (!"0".equals(cursor));
    }


    /**
     *
     *  列表(List)
     *  Redis列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）
     *  一个列表最多可以包含 2^32 - 1 个元素 (4294967295, 每个列表超过40亿个元素)。
     *
     */
    public void listDemo(){
        //将值插入到列表头部
        jedisCluster.lpush("key","val1");
        //将一个值插入到已存在的列表头部
        jedisCluster.lpushx("key","val1");
        //通过索引获取列表中的元素。0开始，可以使用负数下标，以 -1 表示列表的最后一个元素
        jedisCluster.lindex("key",0);
        //移出并获取列表的第一个元素
        jedisCluster.lpop("key");
        //移除并获取列表最后一个元素
        jedisCluster.rpop("key");
        //获取列表长度
        jedisCluster.llen("key");
        //移除表中所有与 VALUE 相等的值
        jedisCluster.lrem("key",0,"val");
    }


    /**
     *  Set 集合
     *  Set 是 String 类型的无序集合
     *  Redis 中集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。
     *  集合中最大的成员数为 2^32 - 1 (4294967295, 每个集合可存储40多亿个成员)。
     */
    public void setDemo(){
        //向集合添加一个或多个成员
        jedisCluster.sadd("key","val");
        //获取集合的成员数
        jedisCluster.scard("key");
        //移除集合中一个或多个成员
        jedisCluster.srem("key","val");
        //返回集合中的所有成员
        jedisCluster.smembers("key");
        //迭代集合中的元素
        String cursor = "0";
        do {
            ScanResult<String> stringScanResult = jedisCluster.sscan("key",cursor);
            cursor = stringScanResult.getStringCursor();
            List<String> stringList = stringScanResult.getResult();
        } while (!"0".equals(cursor));
    }

    /**
     * 有序集合(sorted set)
     * 有序集合和集合一样也是string类型元素的集合,且不允许重复的成员。
     * 每个元素都会关联一个double类型的分数,通过分数来为集合中的成员进行从小到大的排序。
     * 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。 集合中最大的成员数为 2^32 - 1 (4294967295, 每个集合可存储40多亿个成员)。
     *
     */
    public  void sortedSetDemo(){
        //向有序集合添加一个或多个成员，或者更新已存在成员的分数
        jedisCluster.zadd("key",1,"val");
        //获取有序集合的成员数
        jedisCluster.zcard("key");
        //有序集合中对指定成员的分数加上增量 increment
        jedisCluster.zincrby("key",1,"val");
        //移除有序集合中的一个或多个成员
        jedisCluster.zrem("key","val");
        //返回有序集中指定区间内的成员，通过索引，分数从高到底
        jedisCluster.zrevrange("key",0,-1);
    }


}
