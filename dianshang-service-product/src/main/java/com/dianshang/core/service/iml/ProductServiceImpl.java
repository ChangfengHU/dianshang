package com.dianshang.core.service.iml;

import com.dianshang.core.dao.BrandDAO;
import com.dianshang.core.dao.ColorDAO;
import com.dianshang.core.dao.ProductDAO;
import com.dianshang.core.dao.SkuDAO;
import com.dianshang.core.pojo.Brand;
import com.dianshang.core.pojo.Color;
import com.dianshang.core.pojo.Product;
import com.dianshang.core.pojo.Sku;
import com.dianshang.core.service.ProductService;
import com.dianshang.core.tools.PageHelper;
import com.github.abel533.entity.Example;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    private BrandDAO brandDAO;
	@Autowired
    private ProductDAO productDAO;
	@Autowired
    private ColorDAO colorDAO;
	@Autowired
    private SkuDAO skuDAO;
    @Autowired
    private Jedis jedis;
    @Autowired
    private CloudSolrServer cloudSolrServer;
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
        example.setOrderByClause("createTime desc");// 按照创建时间倒叙排列
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
    @Override
    @Transactional
    public void add(Product product) {

        // 设置默认值
        if (product.getIsShow() == null) {
            product.setIsShow(0);
        }
        if (product.getCreateTime() == null) {
            product.setCreateTime(new Date());
        }
        Long pno = jedis.incr("pno");
        product.setId(pno);
        // 先添加商品到数据库中
        productDAO.insert(product);
        System.out.println("获得回显id" + product.getId());

        // 将商品信息添加到库存表中
        // 遍历不同的颜色和尺码
        // 每一个不同颜色，或者不同尺码，都应该插入库存表中，成为一条数据
        String[] colors = product.getColors().split(",");
        String[] sizes = product.getSizes().split(",");

        for (String color : colors) {
            for (String size : sizes) {
                Sku sku = new Sku();
                sku.setProductId(pno);
                sku.setColorId(Long.parseLong(color));
                sku.setSize(size);
                sku.setMarketPrice(1000.00f);
                sku.setPrice(800.00f);
                sku.setDeliveFee(20f);
                sku.setStock(0);
                sku.setUpperLimit(100);
                sku.setCreateTime(new Date());

                skuDAO.insert(sku);
            }
        }
    }

    @Override
    public List<Brand> findEnablebrands() {
        Brand brand = new Brand();
        List<Brand> brands = brandDAO.findByExample(brand);
        return brands;
    }

    @Override
    public void update(Product product, String ids) throws IOException, SolrServerException {
        Example example = new Example(Product.class);

        // 将ids的字符串转成list集合
        List arrayList = new ArrayList();
        String[] split = ids.split(",");
        for (String string : split) {
            arrayList.add(string);
        }

        // 设置批量修改的id条件
        example.createCriteria().andIn("id", arrayList);

        // 进行批量，选择性的非空属性修改
        productDAO.updateByExampleSelective(product, example);
        // 如果是商品上架，将商品信息添加到solr服务器中
        // 需要保存的信息有：商品id、商品名称、图片地址、售价、品牌id、上架时间（可选）
        if (product.getIsShow() == 1) {
            // 查询ids中的所有商品
            List<Product> products = productDAO.selectByExample(example);
            // 遍历查询出来的商品集合
            for (Product product2 : products) {
// 指定要访问的Collection名称
                cloudSolrServer.setDefaultCollection("myCollection1");
                // 将商品的各个信息，添加到文档对象中
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("id", product2.getId());
                doc.addField("name_ik", product2.getName());
                doc.addField("url", product2.getImgUrl().split(",")[0]);
                doc.addField("brandId", product2.getBrandId());

                // 查询出某商品库存中的最低价格
                // SELECT price from bbs_sku WHERE bbs_sku.product_id = 449
                // ORDER BY price ASC LIMIT 1

                Example example2 = new Example(Sku.class);
                // 某商品的库存
                example2.createCriteria().andEqualTo("productId",
                        product2.getId());
                example2.setOrderByClause("price asc");// 价格升序
                // 开始分页 limit
                PageHelper.startPage(1, 1);
                List<Sku> skus = skuDAO.selectByExample(example2);
                // 结束分页
                PageHelper.endPage();

                doc.addField("price", skus.get(0).getPrice());

                // 将文档对象添加到solr服务器中
                cloudSolrServer.add(doc);

                // 提交
                cloudSolrServer.commit();
            }
        }

            }


}
