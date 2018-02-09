package com.dianshang.core.service;

import com.dianshang.core.pojo.Brand;

import java.util.List;

/**
 * 品牌服务类接口
 * 
 * @author Administrator
 *
 */
public interface BrandService {

	/**
	 * 根据条件查询
	 * 
	 * @return
	 */
	public List<Brand> findByExample(Brand brand);

}
