package com.dianshang.core.dao;

import com.dianshang.core.pojo.Sku;
import com.dianshang.core.pojo.SuperPojo;
import com.github.abel533.mapper.Mapper;

import java.util.List;

/**
 * 库存管理DAO
 * @author Administrator
 *
 */
public interface SkuDAO extends Mapper<Sku> {
    /**
     * 根据商品id查询某商品的库存，并且将颜色名称，通过对颜色表连接查询的方式也带出来
     *,传统方式
     * @param productId
     * @return
     */
    List<Sku> findByProductId(String productId);
    /**
     * 根据商品id查询某商品的库存，并且将颜色名称，通过对颜色表连接查询的方式也带出来
     * 大招
     * @param productId
     * @return
     */
    public List<SuperPojo> findSKuAndColorByProductId(Long productId);
}
