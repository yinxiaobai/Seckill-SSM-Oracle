-- 秒杀执行存储过程
-- 定义存储过程
-- 参数 ： in 输入参数; out 输出参数
-- sql%rowcount : 返回上一条修改类型sql(delete,update,insert)的影响行数
-- insert_count : 0:未修改数据;  >0 : 表示修改的行数;  <0 : sql错误/未执行修改sql 
-- 每一句均应以;结尾
-- 注意：oracle中的if else写法为 
/*
  if ... then
  elsif ... then           注意不要写成elseif
  else
  end if;
*/
create or replace procedure seckill.execute_seckill
	(v_seckill_id in number,v_phone in number,
		v_kill_time in timestamp, r_result out number)
as
	--定义一个变量insert_count
	insert_count number;
begin 
	insert /*+ IGNORE_ROW_ON_DUPKEY_INDEX(success_killed(seckill_id,user_phone)) */ 
	into success_killed
		(seckill_id,user_phone,create_time)
		values (v_seckill_id,v_phone,v_kill_time);
	
	insert_count := sql%rowcount;
	if insert_count = 0 then
		rollback;
		r_result := -1;
	elsif insert_count < 0 then
		rollback;
		r_result := -2;
	else 
		update seckill 
		set num = num - 1
		where seckill_id = v_seckill_id
			and end_time > v_kill_time
			and start_time < v_kill_time
			and num > 0;
		insert_count := sql%rowcount;
		if insert_count = 0 then
			rollback;
			r_result := 0;
		elsif insert_count < 0 then 
			rollback;
			r_result := -2;
		else
			commit;
			r_result := 1;
		end if;
	end if;
end;
/
--存储过程定结束