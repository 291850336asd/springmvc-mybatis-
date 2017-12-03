package org.sekill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sekill.dao.SeckillDao;
import org.sekill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private long id =1000;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void getSeckill() throws Exception {
        Seckill seckill = redisDao.getSeckill(id);
        if(seckill == null){
            seckill = seckillDao.queryById(id);
            if(seckill != null){
                String result = redisDao.putSeckill(seckill);
                System.out.println("put result : " + result);
                seckill = redisDao.getSeckill(id);
                System.out.println("get redis result : " + seckill);
            } else{
                System.out.println("mysql result : " + seckill);
            }
        } else {
            System.out.println("already result : " + seckill);
        }
    }

    @Test
    public void putSeckill() throws Exception {
    }

}