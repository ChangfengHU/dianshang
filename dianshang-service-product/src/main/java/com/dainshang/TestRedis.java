package com.dainshang;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestRedis {

	@Autowired
    private Jedis jedis;
	@Autowired
	private HttpSolrServer solrServer;
	@Autowired
	private CloudSolrServer cloudSolrServer;

	/**
	 * 测试使用java代码（jedis）操作 redis服务器 使用Spring管理配置文件
	 */
	@Test
	public void testRedis2() {
		Long incr = jedis.incr("pno");
		System.err.println(incr);
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
		document.addField("id", "4");
		document.addField("title", "还能用单点吗");
		//添加文档到solr服务器对象
		solrServer.add(document);
		// 提交
		solrServer.commit();
		System.err.println("完成");

	}
	/**
	 * 测试使用java代码（jedis）操作 redis服务器
	 */
	@Test
	public void testsolr1() throws SolrServerException, IOException {
		// 使用solr输入文档（SolrInputDocument） 创建文档对象
		SolrInputDocument document = new SolrInputDocument();
		// 添加字段到文档对象
		document.addField("id", "10");
		document.addField("title", "还能用单点吗");
		//添加文档到solr服务器对象
		solrServer.add(document);
		// 提交
		solrServer.commit();
		System.err.println("完成000");

	}
	@Test
	public void testsolrClodWrite() throws Exception{
		// 创建SolrServer
		CloudSolrServer server = new CloudSolrServer("192.168.56.103:2181,192.168.56.104:2181,192.168.56.105:2181");
		// 指定要访问的Collection名称
		server.setDefaultCollection("myCollection1");

		// 创建Document对象
		SolrInputDocument document = new SolrInputDocument();
		// 添加字段
		document.addField("id", "2");
		document.addField("title", "我在项目用使用solrCloud");

		// 添加Document到Server
		server.add(document);
		// 提交
		server.commit();
	}
	@Test
	public void testsolrClodWrite1() throws Exception{
		// 指定要访问的Collection名称
		cloudSolrServer.setDefaultCollection("myCollection1");

		// 创建Document对象
		SolrInputDocument document = new SolrInputDocument();
		// 添加字段
		document.addField("id", "3");
		document.addField("title", "我在项目用使用solrCloud,并使用了spring");

		// 添加Document到Server
		cloudSolrServer.add(document);
		// 提交
		cloudSolrServer.commit();
	}

}
