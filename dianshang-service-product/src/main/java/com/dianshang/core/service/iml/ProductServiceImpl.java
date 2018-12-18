package com.dianshang.core.service.iml;

import com.dianshang.core.dao.BrandDAO;
import com.dianshang.core.dao.ColorDAO;
import com.dianshang.core.dao.ProductDAO;
import com.dianshang.core.dao.SkuDAO;
import com.dianshang.core.pojo.*;
import com.dianshang.core.service.ProductService;
import com.dianshang.core.tools.PageHelper;
import com.github.abel533.entity.Example;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
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
    @Autowired
    private JmsTemplate jmsTemplate;

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
    public void update(Product product, final String ids) throws IOException, SolrServerException {
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
            // 采用消息服务模式
            // 将商品信息添加到solr服务器中（发送消息（ids）到ActiveMQ中）
            jmsTemplate.send("productIds", new MessageCreator() {

                @Override
                public Message createMessage(Session session) throws JMSException {
                    // TODO Auto-generated method stub
                    //使用session创建文本消息
                    return session.createTextMessage(ids);
                }
            });



        }

    }

    @Override
    public SuperPojo findById(Long id) {
        // 先查询出单个商品对象
        Product product = productDAO.selectByPrimaryKey(id);
        // 再根据商品id查询出该商品的库存对象集合
        //此时库存的信息包括了颜色信息
        List<SuperPojo> skus = skuDAO.findSKuAndColorByProductId(id);
        // 将单个商品对象和其对应的库存对象集合封装到万能pojo对象中
        SuperPojo superPojo = new SuperPojo();
        superPojo.setProperty("product", product);
        superPojo.setProperty("skus", skus);
        return superPojo;
    }


}
