package com.dianshang.core.service;

import com.dianshang.core.pojo.SuperPojo;
import com.dianshang.core.tools.PageHelper;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * solr服务类接口
 * 
 * @author Administrator
 *
 */
public interface SolrService {

	/**
     * 根据关键字搜索商品
	 * @param pageNum
	 * @param pageSize @return
	 * @param brandId
	 * @param pa
	 * @param pb
	 * @throws SolrServerException
	 */
	PageHelper.Page<SuperPojo> findProductByKeyWord(String keyword, String sort, Integer pageNum, Integer pageSize, Long brandId, Float pa, Float pb)
			throws SolrServerException;

	/**
	 * 添加商品到solr服务器中
	 *
	 * @param ids
	 * @throws SolrServerException
	 * @throws IOException
	 */
	void addProduct(String ids) throws SolrServerException, IOException;
}
