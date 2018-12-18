package test;

/**
 * Created by 25131 on 2018/2/5.
 */

import com.dianshang.core.dao.ProductDAO;
import com.dianshang.core.dao.SkuDAO;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.io.IOException;

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
    private HttpSolrServer solrServer;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private SkuDAO skuDAO;
    @Test
    public void testAdd()
    {
        System.err.println(skuDAO);
        System.err.println(productDAO);
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
        jedis.set("","胡长风");
        System.err.println(incr);
    }
    /**
     * 测试使用java代码（jedis）操作 redis服务器
     */
    @Test
    public void testsolr() throws SolrServerException, IOException {
        // 使用HttpSolr服务端（HttpSolrServer） 创建solr服务器端对象
        HttpSolrServer solrServer = new HttpSolrServer(
                "http://192.168.56.103:8080/solr/myCollection1");
        // 使用solr输入文档（SolrInputDocument） 创建文档对象
        SolrInputDocument document = new SolrInputDocument();
        // 添加字段到文档对象
        document.addField("id", "100");
        document.addField("title", "好好工作");
        document.addField("url", "www.baicu.com");
        //添加文档到solr服务器对象
        solrServer.add(document);
        // 提交
        solrServer.commit();
        System.err.println("完成");

    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
}
