
CREATE DATABASE seckill;
use seckill;

CREATE TABLE seckill(
  `seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
  `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
  `number` int NOT NULL COMMENT '库存数量',
  `start_time` TIMESTAMP NOT NULL COMMENT '秒杀开启时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';
INSERT INTO seckill(name,number,start_time,end_time) VALUES
  ('1000元秒杀iphone6', 100, '2017-12-10 00:00:00', '2017-12-11 00:00:00'),
  ('500元秒杀小米5', 100, '2017-12-10 00:00:00', '2017-12-11 00:00:00'),
  ('100元秒杀U盘', 100, '2017-12-10 00:00:00', '2017-12-11 00:00:00'),
  ('200元秒杀红米note7', 100, '2017-12-10 00:00:00', '2017-12-11 00:00:00'),
  ('100元秒杀红米note6', 100, '2017-12-1 00:00:00', '2017-12-11 00:00:00'),
  ('100元秒杀红米note9', 100, '2017-12-3 00:00:00', '2017-12-11 00:00:00');
-- 秒杀成功明细表
CREATE TABLE success_killed(
  `seckill_id` bigint NOT NULL COMMENT '商品库存ID',
  `user_phone` bigint NOT NULL COMMENT '用户手机号',
  `state` tinyint NOT NULL DEFAULT -1 COMMENT '状态表示(-1:无效，0:成功，1:已付款，2:已发货)',
  `create_time` TIMESTAMP NOT NULL  COMMENT '创建时间',
  PRIMARY KEY (seckill_id, user_phone), /* 联合主键 */
  key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';