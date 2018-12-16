package com.dianshang.core.service.iml;

import com.dianshang.core.dao.BrandDAO;
import com.dianshang.core.pojo.Brand;
import com.dianshang.core.service.BrandService;
import com.dianshang.core.tools.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 品牌服务类
 *
 * @author Administrator
 */
@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private Jedis jedis;

    @Autowired
    private BrandDAO brandDAO;

    @Override
    public PageHelper.Page<Brand> findByExample(Brand brand, Integer pageNum, Integer pageSize) {
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        List<Brand> byExample = brandDAO.findByExample(brand);
        System.err.println("查询"+byExample.size());
        PageHelper.Page endPage = PageHelper.endPage();// 结束分页
        return endPage;
    }
    @Override
    public Brand findById(Long id) {
        return brandDAO.findById(id);
    }
    @Override
    public void updateById(Brand brand) {
        // 将品牌信息同步修改redis
        jedis.hset("brand", String.valueOf(brand.getId()), brand.getName());


        brandDAO.updateById(brand);
    }

    @Override
    public void deleteById(String id) {
        brandDAO.deleteById(id);
    }

    @Override
    public void deleteByIds(String ids) {
        brandDAO.deleteByIds(ids);
    }

    @Override
    public List<Brand> findAllFromRedis() {
        Map<String, String> hgetAll = jedis.hgetAll("brand");
        // 将查询的结果放入到品牌对象集合中
        List<Brand> brands = new ArrayList<Brand>();

        Set<Map.Entry<String, String>> entrySet = hgetAll.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            Brand brand = new Brand();
            brand.setId(Long.parseLong(entry.getKey()));
            brand.setName(entry.getValue());
            brands.add(brand);
        }
        return brands;

    }

}
