package com.dianshang.core.service.iml;

import com.dianshang.core.dao.ColorDAO;
import com.dianshang.core.dao.ProductDAO;
import com.dianshang.core.pojo.Color;
import com.dianshang.core.pojo.Product;
import com.dianshang.core.service.ProductService;
import com.dianshang.core.tools.PageHelper;
import com.github.abel533.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务类
 * 
 * @author Administrator
 *
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
    private ProductDAO productDAO;
	@Autowired
    private ColorDAO colorDAO;

	@Override
	public PageHelper.Page<Product> findByExample(Product product, Integer pageNum,
												  Integer pageSize) {
		// TODO Auto-generated method stub
       if(product.getName()==null)
		{
			product.setName("");
		}
        Example example = new Example(Product.class);
       example.createCriteria().andLike("name","%"+product.getName()+"%");
        PageHelper.startPage(pageNum,pageSize);
        productDAO.selectByExample(example);
        PageHelper.Page page = PageHelper.endPage();
        System.err.println(page);
        return page;

	}
    @Override
    public List<Color> findEnableColors() {

        Example example = new Example(Color.class);
        example.createCriteria().andNotEqualTo("parentId", 0 + "");

        List<Color> colors = colorDAO.selectByExample(example);
        return colors;
    }


}
