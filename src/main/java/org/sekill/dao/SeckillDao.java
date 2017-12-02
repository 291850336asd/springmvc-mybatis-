package org.sekill.dao;

import org.apache.ibatis.annotations.Param;
import org.sekill.entity.Seckill;

import java.util.Date;
import java.util.List;

public interface SeckillDao {
    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 更新记录行数>=1表示成功
     */
    int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime") Date killTime);

    /**
     * 查询
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);

}
