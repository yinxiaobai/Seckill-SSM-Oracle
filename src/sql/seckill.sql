--创建用户
create user seckill	identified by seckill;
--授权
grant resource,connect,create view to seckill;


--创建秒杀库存表
create table seckill(
       seckill_id  number PRIMARY KEY,
       name varchar2(120) not null,
       num number(3) not null,					  --oracle中不能使用关键字number做列名，这里改为num
       start_time Timestamp  not null,            --时间均为timestamp格式
       end_time timestamp  not null,
       create_time timestamp default systimestamp --默认系统时间戳
       );
--创建序列
create sequence seq_seckill;
--创建索引
create index idx_start_time on seckill(start_time);
create index idx_end_time on seckill(end_time);
create index idx_create_time on seckill(create_time);
--添加注释
COMMENT ON TABLE seckill IS '秒杀库存表';
COMMENT ON COLUMN seckill.seckill_id IS '商品库存ID';
COMMENT ON COLUMN seckill.name IS '商品名称'; 
COMMENT ON COLUMN seckill.num IS '库存数量'; 
COMMENT ON COLUMN seckill.start_time IS '秒杀开启时间'; 
COMMENT ON COLUMN seckill.end_time IS '秒杀结束时间'; 
COMMENT ON COLUMN seckill.create_time IS '创建时间'; 
--初始化数据
--插入时间数据时因只精确到秒，故此时to_date和to_timestamp效果相同，自行选择精确范围
insert into seckill
       (seckill_id,name,num,start_time,end_time)  values 
       (seq_seckill.nextval,'1000元秒杀iphone6', 100, 
        to_date('2016-11-05 12:00:00','yyyy-mm-dd hh24:mi:ss'), to_timestamp('2016-11-06 00:00:12','yyyy-mm-dd hh24:mi:ss'));
insert into seckill
       (seckill_id,name,num,start_time,end_time)  values 
       (seq_seckill.nextval,'500元秒杀ipad2', 200, 
        to_date('2016-11-05 00:12:00','yyyy-mm-dd hh24:mi:ss'), to_timestamp('2016-11-06 00:12:00','yyyy-mm-dd hh24:mi:ss'));
insert into seckill
       (seckill_id,name,num,start_time,end_time)  values 
       (seq_seckill.nextval,'300元秒杀小米4', 300, 
        to_date('2016-11-05 00:00:12','yyyy-mm-dd hh24:mi:ss'), to_timestamp('2016-11-06 12:00:00','yyyy-mm-dd hh24:mi:ss'));
insert into seckill
       (seckill_id,name,num,start_time,end_time)  values 
       (seq_seckill.nextval,'200元秒杀红米note', 400,
        to_date('2016-11-05 00:00:01','yyyy-mm-dd hh24:mi:ss'), to_timestamp('2016-11-06 00:00:12','yyyy-mm-dd hh24:mi:ss'));
--提交数据
commit
/*
  因oracle数据库直接查询timestamp格式时显示为西方日期格式，不符合常用习惯，查询时可通过to_char转换
  网上有教程通过在oracle的注册表中添加 NLS_TIMESTAMP_FORMAT = YYYY-MM-DD HH24:MI:SS:FF6 
  字符串本人脸黑，未成功-.-,有兴趣自行百度，成功了请传授我下经验
*/
select to_char(start_time,'yyyy-mm-dd hh24:mi:ss:ff6'),
       to_char(end_time,'yyyy-mm-dd hh24:mi:ss:ff6'),
       to_char(create_time,'yyyy-mm-dd hh24:mi:ss:ff6') from seckill;

--查询oracle默认timestamp处理格式：nls_timestamp_format
select value from nls_session_parameters where parameter = 'nls_timestamp_format'



--秒杀成功明细表
--用户登陆认证相关的信息
create table success_killed(
       seckill_id  number,
       user_phone varchar2(120) not null,
       state number(1) default -1,
       create_time timestamp default systimestamp,
       primary  key (seckill_id,user_phone) /*联合主键*/
       
);
--添加注释
COMMENT ON TABLE success_killed IS '秒杀成功明细表';
COMMENT ON COLUMN success_killed.seckill_id IS '秒杀商品id';
COMMENT ON COLUMN success_killed.user_phone IS '用户手机号';
COMMENT ON COLUMN success_killed.state IS '状态标示：-1无效 0：成功 1：已付款 2：已发货';
COMMENT ON COLUMN success_killed.create_time IS '创建时间';

--创建索引
create index idx_create_time on success_killed(create_time);