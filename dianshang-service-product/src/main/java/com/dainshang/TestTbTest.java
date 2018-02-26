package com.dainshang;

/**
 * Created by 25131 on 2018/2/5.
 */

import com.dianshang.core.dao.TestTbDAO;
import com.dianshang.core.pojo.TestTb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * Junit + Spring
 *
 * @author Administrator
 * 这样就不用写代码来加载applicationContext.xml和getBean了
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})

public class TestTbTest {
    @Autowired
    private TestTbDAO testTbDAO;

    @Test
    public void testAdd()
    {
        TestTb testTb = new TestTb();
        testTb.setName("范冰冰");
        testTb.setBirthday(new Date());
        System.err.println("11111111111111111111111111111111"+testTbDAO);
        testTbDAO.add1(testTb);
    }
    /**
     * 测试使用java代码（jedis）操作 redis服务器
     */
    @Test
    public void testRedis() {
        // 创建redis客户端对象并指定服务器地址 端口默认为6379
        Jedis jedis = new Jedis("192.168.57.101", 6379);
        // 使redis中的pno key值加1
        Long incr = jedis.incr("pno");
        System.err.println("索引"+incr);
    }

}
