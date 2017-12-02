package org.sekill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sekill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccesskillDaoTest {

    @Resource
    private SuccesskillDao successkillDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        long phone = 13212323431L;
        int result = successkillDao.insertSuccessKilled(1000, phone);
        System.out.println("result : " + result);
    }

    @Test
    public void queryByIdWithBySeckill() throws Exception {
        long phone = 13212323431L;
        SuccessKilled successKilled = successkillDao.queryByIdWithBySeckill(1000, phone);
        System.out.println(successKilled.toString());
    }

}