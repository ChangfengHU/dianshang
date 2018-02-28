package com.dianshang.core.service;

import com.dianshang.core.pojo.Sku;

import java.util.List;

/**
 * 库存service接口
 * Created by Administrator on 2017/8/23 0023.
 */
public interface SkuService {

    List<Sku> findByProductId(String productId);

    int update(Sku sku);
}
