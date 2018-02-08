package com.dianshang;

/**
 * Created by 25131 on 2018/2/5.
 */

import com.dianshang.core.dao.TestTbDAO;
import com.dianshang.core.pojo.TestTb;
import com.dianshang.core.service.TestTbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Junit + Spring
 *
 * @author Administrator
 * 这样就不用写代码来加载applicationContext.xml和getBean了
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})

public class TestTbTest1 {
    @Autowired
    private TestTbDAO testTbDAO;
    @Autowired
    private TestTbService testTbService;

    @Test
    public void testAdd()
    {
        TestTb testTb = new TestTb();
        testTb.setName("范冰冰");
        testTb.setBirthday(new Date());
        System.err.println("11111111"+testTbDAO);
        testTbDAO.add1(testTb);
    }
    @Test
    public void testAddService()
    {
        TestTb testTb = new TestTb();
        testTb.setName("刘德华");
        testTb.setBirthday(new Date());
        System.err.println("service:"+testTbService);
        testTbService.add(testTb);
    }
}
