package org.sekill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.sekill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static com.dyuproject.protostuff.LinkedBuffer.DEFAULT_BUFFER_SIZE;

public class RedisDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private JedisPool jedisPool;
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
    public RedisDao(String ip, int port){
        jedisPool = new JedisPool(ip, port);
    }

    public Seckill getSeckill(long seckillId){
        //redis操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try{
                String key = "seckill:" + seckillId;
                //jedis并没有实现内部序列化操作
                //get->byte[] ->反序列化->object(seckill)
                byte[] bytes = jedis.get(key.getBytes());
                if(bytes != null){
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    return seckill;
                }
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public  String putSeckill(Seckill seckill){
        //set object(seckill) -> bytes[]
        try{
            Jedis jedis = jedisPool.getResource();
            try{
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(DEFAULT_BUFFER_SIZE));
                int timeTout = 60 * 60;//一小时
                String result = jedis.setex(key.getBytes(), timeTout, bytes);
                return result;
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
