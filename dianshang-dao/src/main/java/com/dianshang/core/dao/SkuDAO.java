package com.dianshang.core.dao;

import com.dianshang.core.pojo.Sku;
import com.github.abel533.mapper.Mapper;

import java.util.List;

/**
 * 库存管理DAO
 * @author Administrator
 *
 */
public interface SkuDAO extends Mapper<Sku> {
    List<Sku> findByProductId(String productId);

}
