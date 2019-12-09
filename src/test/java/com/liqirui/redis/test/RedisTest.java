package com.liqirui.redis.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.liqirui.redis.test.entity.User;
import com.rui.common.utils.DateUtil;
import com.rui.common.utils.RandomUtils;
import com.rui.common.utils.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-redis.xml")
public class RedisTest {

	@Resource
	private RedisTemplate redisTemplate;

	// 4. 使用Redis的list类型保存5万个user随机对象到Redis，并计算耗时
	@Test
	public void testJDK() {

		ListOperations opsForList = redisTemplate.opsForList();
		ArrayList list = new ArrayList();
		Calendar c = Calendar.getInstance();
		
		for (int i = 1; i <= 50000; i++) {

			// (1) ID使用1-5万的顺序号
			Integer id = i;

			// (2) 姓名使用3个随机汉字模拟，可以使用以前自己编写的工具方法。（4分）
			String name = StringUtil.randomChineseString(3);

//			(3)	性别在女和男两个值中随机。（4分）
			String[] str1 = { "男", "女" };
			String sex = str1[RandomUtils.random(0, 1)];

//			(4)	手机以13开头+9位随机数模拟。（4分）
			String phone = "13" + RandomUtils.randomNumber(9);

//			(5)	邮箱以3-20个随机字母 + @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.com模拟。（4分）
			String[] str2 = { "@qq.com", "@163.com", "@sian.com", "@gmail.com", "@sohu.com", "@hotmail.com",
					"@foxmail.com" };
			String email = RandomUtils.randomString((RandomUtils.random(3, 20))) + str2[RandomUtils.random(0, 6)];

//			(6)	生日要模拟18-70岁之间，即日期从1949年到2001年之间。（4分）
			c.set(1949, 0, 1);
			Date t1 = c.getTime();
			c.set(2001, 0, 1);
			Date t2 = c.getTime();
			Date time = DateUtil.randomDate(t1, t2);

			User user = new User(id, name, sex, phone, email, time);
			list.add(user);
		}

		// 开始时间
		long l1 = System.currentTimeMillis();

		// (3) 使用RedisTemplate保存上述模拟的5万个user对象到Redis
		opsForList.leftPush("user_jdk", list);

		// 开始时间
		long l2 = System.currentTimeMillis();
		System.out.println("花费时间：" + (l2 - l1) + "毫秒");
	}

	// 5. 使用Redis的Json类型保存5万个user随机对象到Redis，并计算耗时
	@Test
	public void testJson() {
		
		ListOperations opsForList = redisTemplate.opsForList();
		ArrayList list = new ArrayList();
		Calendar c = Calendar.getInstance();

		for (int i = 1; i <= 50000; i++) {

			// (1) ID使用1-5万的顺序号
			Integer id = i;

			// (2) 姓名使用3个随机汉字模拟，可以使用以前自己编写的工具方法。（4分）
			String name = StringUtil.randomChineseString(3);

//				(3)	性别在女和男两个值中随机。（4分）
			String[] str1 = { "男", "女" };
			String sex = str1[RandomUtils.random(0, 1)];

//				(4)	手机以13开头+9位随机数模拟。（4分）
			String phone = "13" + RandomUtils.randomNumber(9);

//				(5)	邮箱以3-20个随机字母 + @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.com模拟。（4分）
			String[] str2 = { "@qq.com", "@163.com", "@sian.com", "@gmail.com", "@sohu.com", "@hotmail.com",
					"@foxmail.com" };
			String email = RandomUtils.randomString((RandomUtils.random(3, 20))) + str2[RandomUtils.random(0, 6)];

//				(6)	生日要模拟18-70岁之间，即日期从1949年到2001年之间。（4分）
			c.set(1949, 0, 1);
			Date t1 = c.getTime();
			c.set(2001, 0, 1);
			Date t2 = c.getTime();
			Date time = DateUtil.randomDate(t1, t2);

			User user = new User(id, name, sex, phone, email, time);

			list.add(user);
		}

		// 开始时间
		long l1 = System.currentTimeMillis();

		// (3) 使用RedisTemplate保存上述模拟的5万个user对象到Redis
		opsForList.leftPushAll("user_json", list);
		
		// 开始时间
		long l2 = System.currentTimeMillis();
		System.out.println("花费时间：" + (l2 - l1) + "毫秒");
	}

	// 6. 使用Redis的Hash类型保存5万个user随机对象到Redis，并计算耗时
	@Test
	public void testHash() {

		HashOperations opsForHash = redisTemplate.opsForHash();
		HashMap<String, User> map = new HashMap<String, User>();
		Calendar c = Calendar.getInstance();

		for (int i = 1; i <= 50000; i++) {

			// (1) ID使用1-5万的顺序号
			Integer id = i;

			// (2) 姓名使用3个随机汉字模拟，可以使用以前自己编写的工具方法。（4分）
			String name = StringUtil.randomChineseString(3);

//			(3)	性别在女和男两个值中随机。（4分）
			String[] str1 = { "男", "女" };
			String sex = str1[RandomUtils.random(0, 1)];

//			(4)	手机以13开头+9位随机数模拟。（4分）
			String phone = "13" + RandomUtils.randomNumber(9);

//			(5)	邮箱以3-20个随机字母 + @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.com模拟。（4分）
			String[] str2 = { "@qq.com", "@163.com", "@sian.com", "@gmail.com", "@sohu.com", "@hotmail.com",
					"@foxmail.com" };
			String email = RandomUtils.randomString((RandomUtils.random(3, 20))) + str2[RandomUtils.random(0, 6)];

//			(6)	生日要模拟18-70岁之间，即日期从1949年到2001年之间。（4分）
			c.set(1949, 0, 1);
			Date t1 = c.getTime();
			c.set(2001, 0, 1);
			Date t2 = c.getTime();
			Date time = DateUtil.randomDate(t1, t2);

			User user = new User(id, name, sex, phone, email, time);
			map.put("" + i, user);
		}

		// 开始时间
		long l1 = System.currentTimeMillis();

		// (3) 使用RedisTemplate保存上述模拟的5万个user对象到Redis
		opsForHash.putAll("user_hash", map);

		// 开始时间
		long l2 = System.currentTimeMillis();
		System.out.println("花费时间：" + (l2 - l1) + "毫秒");
	}
}
