package org.sekill.service.impl;

import org.sekill.dao.SeckillDao;
import org.sekill.dao.SuccesskillDao;
import org.sekill.dto.Exposer;
import org.sekill.dto.SeckillExecution;
import org.sekill.entity.Seckill;
import org.sekill.entity.SuccessKilled;
import org.sekill.enums.SeckillStateEnum;
import org.sekill.exception.RepeatKillException;
import org.sekill.exception.SeckillCloseException;
import org.sekill.exception.SeckillException;
import org.sekill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String slat = "*^%121sdxx&*%^$";

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccesskillDao successkillDao;

    public List<Seckill> getSeckllList() {
        return seckillDao.queryAll(0, 10);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if(seckill == null){
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()){
            return new Exposer(false, seckillId,nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 =getMD5(seckillId);//TODO
        return new Exposer(true, md5,seckillId);
    }

    @Transactional
    /**
     * 明确标注事务风格
     * 保证事务方法执行时间尽可能短，不要穿插其他网络操作或者将耗时操作剥离到事务操作方法外
     * 不是所方法都需要事务
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        try{
            //减库存 + 购买行为
            Date nowDate = new Date();
            int updateCount = seckillDao.reduceNumber(seckillId,nowDate);
            if(updateCount <= 0){
                //没有跟新到记录
                throw new SeckillCloseException("seckill is closed");
            } else {
                //记录购买行为
                int insertCount = successkillDao.insertSuccessKilled(seckillId, userPhone);
                //seckillId, userPhone联合主键
                if(insertCount <= 0){
                    throw new RepeatKillException("seckill repeated");
                }else {
                    //秒杀成功
                    SuccessKilled successKilled = successkillDao.queryByIdWithBySeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
                }
            }
        }catch (SeckillCloseException e){
            throw e;
        }catch (RepeatKillException e){
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            //所有编译器异常转化为运行期异常
            throw new SeckillException("seckill inner error : " + e.getMessage());

        }
    }

    private String getMD5(long seckillId){
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
