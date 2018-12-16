package com.dianshang.core.service.imp;

import com.dianshang.core.pojo.SuperPojo;
import com.dianshang.core.service.SolrService;
import com.dianshang.core.tools.PageHelper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * solr服务类
 * 
 * @author Administrator
 *
 */
@Service("solrService")
public class SolrServiceImpl implements SolrService {

	@Autowired
	private CloudSolrServer cloudSolrServer;

	@Override
	public PageHelper.Page<SuperPojo> findProductByKeyWord(String keyword, String sort, Integer pageNum, Integer pageSize, Long brandId, Float pa, Float pb)
			throws SolrServerException {
		System.err.println("solr查询进来了吗");
		// 设置查询条件
		// 指定要访问的Collection名称
		cloudSolrServer.setDefaultCollection("myCollection1");
		String query=null;
		query="name_ik:" + keyword;
		if (StringUtils.isEmpty(keyword)){
			query="name_ik:*";
		}
		SolrQuery solrQuery = new SolrQuery(query);
		// 设置过滤条件
		if (brandId != null) {
			solrQuery.addFilterQuery("brandId:" + brandId);// 品牌
		}

		// 价格
		if (pa != null && pb != null) {
			if (pb == -1) {
				solrQuery.addFilterQuery("price:[" + pa + " TO *]");
			} else {
				solrQuery.addFilterQuery("price:[" + pa + " TO " + pb + "]");
			}
		}

		// 设置排序
        // solrQuery.setSort("price", ORDER.asc);
        if (sort != null && sort.trim().length() > 0) {
            String s = sort.split(",")[0];
            String s1 = sort.split(",")[1];
            SolrQuery.SortClause sortClause = new SolrQuery.SortClause(s, s1);
            solrQuery.setSort(sortClause);
        }

        // 开始分页设置
        PageHelper.Page page = new PageHelper.Page(pageNum, pageSize);

        solrQuery.setStart(page.getStartRow());
        solrQuery.setRows(page.getPageSize());


        // 设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("name_ik");
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost("</span>");

		// 开始查询
		QueryResponse response = cloudSolrServer.query(solrQuery);
        // 获得高亮数据集合
        Map<String, Map<String, List<String>>> highlighting = response
                .getHighlighting();


        // 获得结果集
		SolrDocumentList results = response.getResults();

// 获得总数量
        long total = results.getNumFound();
        page.setTotal(total);


        // 将结果集中的信息封装到商品对象中
		// 注意：由于原商品对象中并没有价格属性，而价格属性本应该是在商品对象的子对象库存对象中，
		// 而本次设计并不打算使用类似于hibernate的在pojo中做对象的相应关联，所以这里，我们可以使用万能对象来装载数据
		// 一个万能对象就可以等同于从数据库查询（包括连接查询）出的结果表中的一条数据

		// 创建商品对象（万能对象）集合
		List<SuperPojo> superProducts = new ArrayList<SuperPojo>();

		for (SolrDocument solrDocument : results) {
			// 创建商品对象
			SuperPojo superProduct = new SuperPojo();
			// 商品id
			String id = (String) solrDocument.get("id");
			superProduct.setProperty("id", id);

			// 商品名称
//			String name = (String) solrDocument.get("name_ik");
//			superProduct.setProperty("name", name);
            // 取得高亮数据集合中的商品名称
            Map<String, List<String>> map = highlighting.get(id);
            String string = map.get("name_ik").get(0);
            superProduct.setProperty("name", string);

			// 图片地址
			String imgUrl = (String) solrDocument.get("url");
			superProduct.setProperty("imgUrl", imgUrl);

			// 商品最低价格
			Float price = (Float) solrDocument.get("price");
			superProduct.setProperty("price", price);

			// 品牌id
			String brandId1 =  ""+solrDocument.get("brandId");
			superProduct.setProperty("brandId", brandId1);

			// 将万能商品对象添加到集合中
			superProducts.add(superProduct);
		}
        // 将结果添加到分页对象中
        page.setResult(superProducts);

        return page;
	}
}
