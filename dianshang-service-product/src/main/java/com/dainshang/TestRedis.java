package com.dainshang;

/**
 * Created by 25131 on 2018/2/5.
 */

import com.dianshang.core.dao.ProductDAO;
import com.dianshang.core.dao.TestTbDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Junit + Spring
 *
 * @author Administrator
 * 这样就不用写代码来加载applicationContext.xml和getBean了
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})

public class TestRedis {
    @Autowired
    private TestTbDAO testTbDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private JmsTemplate jmsTemplate;
    @Test
    public void testAdd()
    {
        System.out.println(productDAO);
    }
    /**
     * 测试使用java代码（jedis）操作 redis服务器
     */
    @Test
    public void testRedis() {
        // 创建redis客户端对象并指定服务器地址 端口默认为6379
        Jedis jedis = new Jedis("192.168.56.200", 6379);
        // 使redis中的pno key值加1
        Long incr = jedis.incr("pno");
        String key = jedis.get("key");
        System.err.println("索引"+key);
    }
    /**
     * 测试使用java代码（jedis）操作 redis服务器
     */
    @Test
    public void testActiveMQ() {
        // 采用消息服务模式
        // 将商品信息添加到solr服务器中（发送消息（ids）到ActiveMQ中）
        jmsTemplate.send("productIds", new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                // TODO Auto-generated method stub
                //使用session创建文本消息
                return session.createTextMessage("我来发消息02");
            }
        });

        // 后续还有CMS-生成静态商品信息页面功能
    }

}
