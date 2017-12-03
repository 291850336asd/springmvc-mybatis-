package org.sekill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sekill.dto.Exposer;
import org.sekill.dto.SeckillExecution;
import org.sekill.entity.Seckill;
import org.sekill.exception.RepeatKillException;
import org.sekill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckllList() throws Exception {
        List<Seckill> list = seckillService.getSeckllList();
        logger.info("list={}", list);
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1004);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
       try{
           SeckillExecution seckillExecution = seckillService.executeSeckill(1004L, 13212323431L,"e3e7f0757d8d0bd4809d043e429ca3ff");
           logger.info("seckillExecution={}",seckillExecution);
       }catch (SeckillCloseException e){
           logger.info("seckillExecution={}",e.getMessage());
       }catch (RepeatKillException e){
           logger.info("seckillExecution={}",e.getMessage());
       }catch (Exception e){
           logger.info("seckillExecution={}",e.getMessage());
       }
    }

}