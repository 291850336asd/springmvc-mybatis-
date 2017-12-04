package com.quartz;

import org.junit.runner.RunWith;
import org.sekill.dao.SeckillDao;
import org.sekill.entity.Seckill;
import org.sekill.quratz.AnotherBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 配置spring和junit整合，junit启动时加载Spring容器
 */
public class Test {

        public static void main(String[] args) {
            ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/spring-quartz.xml");
        }
}