package com.dianshang.core.dao;

import com.dianshang.core.pojo.Brand;

import java.util.List;

/**
 * 品牌管理DAO
 *
 * @author Administrator
 */
public interface BrandDAO {

    /**
     * 根据条件查询
     *
     * @return
     */
    public List<Brand> findByExample(Brand brand);

    /**
     * 根据id查询 单个品牌对象信息
     * @param id
     * @return
     */
    public Brand findById(Long id);
    /**
     * 修改单个品牌对象信息
     * @param brand
     */
    public void updateById(Brand brand);
    /**
     * 删除单个品牌对象信息
     * @param ids
     */
    public void deleteById(String ids);

    /**
     * 根据多个id删除品牌对象
     *
     * @param ids
     */
    public void deleteByIds(String ids);



}