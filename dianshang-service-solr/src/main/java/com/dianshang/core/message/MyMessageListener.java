package com.dianshang.core.message;

import com.dianshang.core.service.SolrService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.IOException;

/**
 * 自定义消息监听器类
 * 
 * @author Administrator
 *
 */
public class MyMessageListener implements MessageListener {

	@Autowired
    private SolrService solrService;

	/**
	 * 当监听到消息后，会自动调用此方法
	 */
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		ActiveMQTextMessage amessage = (ActiveMQTextMessage) message;
		try {
			String ids = amessage.getText();
			System.out.println("消费方接收到的消息：" + ids);
			// 添加商品信息到solr服务器
			solrService.addProduct(ids);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}