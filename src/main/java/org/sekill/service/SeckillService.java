package org.sekill.service;

import org.sekill.dto.Exposer;
import org.sekill.dto.SeckillExecution;
import org.sekill.entity.Seckill;
import org.sekill.exception.RepeatKillException;
import org.sekill.exception.SeckillCloseException;
import org.sekill.exception.SeckillException;

import java.util.List;

public interface SeckillService {

    List<Seckill> getSeckllList();

    Seckill getById(long seckillId);

    /**
     * 是否输出秒杀接口的地址，否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException,RepeatKillException,SeckillCloseException;

}
