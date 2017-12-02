package org.sekill.dao;

import org.apache.ibatis.annotations.Param;
import org.sekill.entity.Seckill;
import org.sekill.entity.SuccessKilled;

import java.util.Date;
import java.util.List;

public interface SuccesskillDao {

    /**
     * 插入购买明细
     * @param seckillId
     * @param userPhone
     * @return 插入行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /**
     * 根据id查询successkilled并携带产品对象
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithBySeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
