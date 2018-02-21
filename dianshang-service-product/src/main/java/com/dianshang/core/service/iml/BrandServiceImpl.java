package com.dianshang.core.service.iml;

import com.dianshang.core.dao.BrandDAO;
import com.dianshang.core.pojo.Brand;
import com.dianshang.core.service.BrandService;
import com.dianshang.core.tools.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 品牌服务类
 *
 * @author Administrator
 */
@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {

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
        brandDAO.updateById(brand);
    }

    @Override
    public void deleteById(String id) {
        brandDAO.deleteById(id);
    }

}
