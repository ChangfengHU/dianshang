package com.dianshang.core.service;

import com.dianshang.core.pojo.Brand;
import com.dianshang.core.pojo.Color;
import com.dianshang.core.pojo.Product;
import com.dianshang.core.pojo.SuperPojo;
import com.dianshang.core.tools.PageHelper;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

/**
 * 商品服务接口
 * 
 * @author Administrator
 *
 */
public interface ProductService {
    /**
	 * 根据条件查询 带分页
	 * 
	 * @param product
	 *            查询条件的模版对象
	 * @param pageNum
	 *            当前页码
	 * @param pageSize
	 *            每页显示行数
	 * @return
	 */
	public PageHelper.Page<Product> findByExample(Product product, Integer pageNum,
												  Integer pageSize);

	/**
	 * 查询所有可用颜色（颜色的父id不为0）
	 *
	 * @return
	 */
	public List<Color> findEnableColors();

	/**
	 * 添加商品
	 * @param product
	 */
	public void add(Product product);

	/**
	 * 查询所有可用颜色（颜色的父id不为0）
	 *
	 * @return
	 */
	public List<Brand> findEnablebrands();

	/**
	 * 商品上下架
	 * @param product
	 * @param ids
	 * @throws IOException
	 * @throws SolrServerException
	 */
	void update(Product product, String ids) throws IOException, SolrServerException;

	/**
	 * 根据商品id查询单个商品信息
	 * @param id
	 * @return
	 */
	public SuperPojo findById(Long id);
}
