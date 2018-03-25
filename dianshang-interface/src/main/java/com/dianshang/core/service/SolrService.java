package com.dianshang.core.service;

import com.dianshang.core.pojo.SuperPojo;
import com.dianshang.core.tools.PageHelper;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * solr服务类接口
 * 
 * @author Administrator
 *
 */
public interface SolrService {

	/**
     * 根据关键字搜索商品
	 * 
	 * @param keyWord
	 * @param s
     *@param pageNum
     * @param pageSize @return
	 * @throws SolrServerException
	 */
	PageHelper.Page<SuperPojo> findProductByKeyWord(String keyword, String sort, Integer pageNum, Integer pageSize)
			throws SolrServerException;

}
