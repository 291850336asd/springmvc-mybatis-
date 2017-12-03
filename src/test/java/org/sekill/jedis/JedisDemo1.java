package org.sekill.jedis;

import org.junit.Test;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisDemo1 {

    /**
     * 字符串键值对
     */
    @Test
    public void demo1(){
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("name", "nihao");
        String value = jedis.get("name");
        System.out.println(value);
        jedis.close();
    }

    /**
     * 连接池连接
     * 字符串操作
     */
    @Test
    public void demoPool1(){
        JedisPoolConfig config = new JedisPoolConfig();
        //最大连接数
        config.setMaxTotal(30);
        //最大空闲数
        config.setMaxIdle(10);
        JedisPool pool = new JedisPool(config, "localhost", 6379);
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.set("name", "nihao1");
            String value = jedis.get("name");
            System.out.println(value);
            jedis.del("name");
            value = jedis.get("name");
            System.out.println(value);
            jedis.incr("name");
            value = jedis.get("name");
            System.out.println(value);
            jedis.decr("name");
            value = jedis.get("name");
            System.out.println(value);

            //数值+10
            jedis.incrBy("name",10);
            value = jedis.get("name");
            System.out.println(value);
            //数值-10
            jedis.decrBy("name", 5);
            value = jedis.get("name");
            System.out.println(value);

            //追加字符串
            jedis.append("name", "5");
            value = jedis.get("name");
            System.out.println(value);
            //数值+10
            jedis.incrBy("name",10);
            value = jedis.get("name");
            System.out.println(value);
        }finally {
            if(jedis != null){
                jedis.close();
            }
            if(pool != null){
                pool.close();
            }
        }
    }


    /**
     * hash键值对
     */
    @Test
    public void demoHash(){
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.hset("hashname", "name", "jack");
        jedis.hset("hashname", "age", "18");

        Map<String, String> maps = new HashMap<String, String>();
        maps.put("haha","ok");
        maps.put("ok","haha");
        maps.put("age","1");
        jedis.hmset("hashmap",maps);
        String value = jedis.hget("hashname", "name");
        System.out.println(value);

        List<String> values = jedis.hmget("hashmap","haha");
        System.out.println(values.toArray().toString());

        Map<String, String> mapsInfo = jedis.hgetAll("hashname");
        System.out.println(mapsInfo.values().toString());


        jedis.hdel("hashname", "name");
        mapsInfo = jedis.hgetAll("hashname");
        System.out.println(mapsInfo.values().toString());


        jedis.del("hashname");
        mapsInfo = jedis.hgetAll("hashname");
        System.out.println(mapsInfo.values().toString());

        jedis.hincrBy("hashmap","age", 5);
        mapsInfo = jedis.hgetAll("hashmap");
        System.out.println(mapsInfo.values().toString());

        jedis.hexists("hashmap", "age");//是否存在，1为存在  0为否

        System.out.println("hashmap keys length " + jedis.hlen("hashmap"));
        jedis.close();
    }


    /**
     * List 根据插入顺讯排序
     * ArrayList  LinkList
     */
    @Test
    public void demoList(){
        Jedis jedis = new Jedis("localhost", 6379);

        jedis.del("mylist");
        jedis.del("mylist2");
        jedis.lpush("mylist", "a","b","c");
        jedis.lpush("mylist", "1","2","3");
        jedis.rpush("mylist2", "a","b","c");
        jedis.rpush("mylist2", "1","2","3");
        List<String> values = jedis.lrange("mylist", 0, 5);
        System.out.println(values.toString());
         values = jedis.lrange("mylist2", 0, 5);
        System.out.println(values.toString());

        System.out.println(jedis.lpop("mylist"));
        values = jedis.lrange("mylist", 0, 5);
        System.out.println(jedis.llen("mylist"));
        System.out.println(values.toString());

        jedis.lpushx("mylist", "pp");
        System.out.println(jedis.llen("mylist"));
        values = jedis.lrange("mylist", 0, -1);
        System.out.println(values.toString());

        jedis.lrem("mylist", 2,"a");
        values = jedis.lrange("mylist", 0, -1);
        System.out.println(values.toString());

        jedis.lset("mylist", 0, "dsds");
        values = jedis.lrange("mylist", 0, -1);
        System.out.println(values.toString());

        jedis.linsert("mylist", BinaryClient.LIST_POSITION.BEFORE,"b" ,"dsds");
        values = jedis.lrange("mylist", 0, -1);
        System.out.println(values.toString());

        jedis.rpoplpush("mylist","mylist2");
        jedis.close();
    }


    /**
     * 不允许出现相同元素
     * set未排序
     */
    @Test
    public void demoSet(){
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.sadd("myset","a","b","c");
        jedis.sadd("myset","a","1","2");
        Set<String> values = jedis.smembers("myset");
        System.out.println(values.toString());
        System.out.println(jedis.sismember("myset","1"));
        System.out.println(jedis.sismember("myset","10"));

        jedis.sadd("myset2","a1","11","2");

        System.out.println(jedis.sdiff("myset","myset2").toString());
        System.out.println(jedis.sdiff("myset2","myset").toString());
        System.out.println("inter :" + jedis.sinter("myset2","myset").toString());
        System.out.println(jedis.sunion("myset2","myset").toString());

        System.out.println(jedis.scard("myset2"));

        System.out.println(jedis.srandmember("myset2"));

        jedis.sdiffstore("mysetstro","myset","myset2");
        Set<String> mysetstro = jedis.smembers("mysetstro");
        System.out.println(mysetstro.toString());
        jedis.close();
    }

    /**
     * 不允许出现相同元素
     * orderset排序
     */
    @Test
    public void demoOrderSet(){
        Jedis jedis = new Jedis("localhost", 6379);

        jedis.zadd("zsort",70, "nihao");
        jedis.zadd("zsort",80, "ni");
        jedis.zadd("zsort",75, "nao");
        jedis.zadd("zsort",80, "ni");
        jedis.zadd("zsort",90, "hao");
        jedis.zadd("zsort",190, "www");
        jedis.zadd("zsort",70, "hao");
        jedis.zadd("zsort",95, "eww");
        double result = jedis.zscore("zsort","hao");
        System.out.println(result);
        jedis.zrem("zsort","hao");
//        result = jedis.zscore("zsort","hao"); nullpoint
//        System.out.println(result);
        System.out.println("length : " + jedis.zcard("zsort"));
        System.out.println(jedis.zrange("zsort", 0 ,-1).toString());
        System.out.println(jedis.zrangeWithScores("zsort", 0 ,-1).toString());
        System.out.println(jedis.zrevrangeWithScores("zsort", 0 ,-1).toString());

        // jedis.zremrangeByRank("zsort", 0,2);
        //jedis.zremrangeByScore("zsort",70,100);
        //System.out.println(jedis.zrange("zsort", 0 ,-1).toString());
//        System.out.println(jedis.keys("*").toString());
//        System.out.println(jedis.exists("myset"));
//        jedis.rename("myset","mysetnow");
//        System.out.println(jedis.exists("myset"));
      //  jedis.expire("xxx",10);
      //  jedis.ttl("xxx");//剩余超时时间，未设置返回-1；
      //  jedis.type("xxx");//返回类型
        jedis.close();
    }
}
