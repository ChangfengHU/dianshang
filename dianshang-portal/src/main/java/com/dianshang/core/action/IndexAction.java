package com.dianshang.core.action;

import com.dianshang.core.pojo.Brand;
import com.dianshang.core.pojo.SuperPojo;
import com.dianshang.core.service.BrandService;
import com.dianshang.core.service.SolrService;
import com.dianshang.core.tools.Encoding;
import com.dianshang.core.tools.PageHelper;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.TreeMap;

/**
 * 前台首页控制中心
 * 
 * @author Administrator
 *
 */
@Controller
public class IndexAction {
	@Autowired
	private SolrService solrService;
	@Autowired
	private BrandService brandService;


	//显示前台首页
	@RequestMapping(value = "portal/{pageName}.do")
	public String consoleShow(@PathVariable(value = "pageName") String pageName) {
		System.out.println("主页进来了");
		return pageName;
	}

	// 前台首页搜索功能
	@RequestMapping(value = "/search.do")
	public String indexSearch(Model model, String keyword,String sort,
							  Integer pageNum, Integer pageSize,Long brandId, Float pa, Float pb
	)
			throws SolrServerException {
		System.err.println("solr查询进来了吗前台");
		keyword = Encoding.encodeGetRequest(keyword);
		PageHelper.Page<SuperPojo> pageSolr = solrService
				.findProductByKeyWord(keyword, sort,pageNum,pageSize,brandId,pa,pb);
		int begin;
		int end;

		// 计算显示的起始页码（根据当前页码计算）：当前页码-5
		begin = pageSolr.getPageNum() - 5;
		if (begin < 1) {// 页码修复
			begin = 1;
		}
		// 计算显示的结束页码（根据开始页码计算）：开始页码+9
		end = begin + 9;
		if (end > pageSolr.getPages()) {// 页码修复
			end = pageSolr.getPages();
		}
		model.addAttribute("keyword1", pageSolr);

		//回显查询的关键字
		model.addAttribute("keyword", keyword);
		// 将反转前的sort丢给页面 sort2
		model.addAttribute("sort2", sort);
		model.addAttribute("brandId", brandId);
		// 反转排序规则
		if (sort!=null&&sort.equals("price,asc")) {
			sort = "price,desc";
		} else {
			sort = "price,asc";
		}

		model.addAttribute("sort", sort);

		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		// 从redis中查询出品牌，并传递到页面
		List<Brand> brands = brandService.findAllFromRedis();
		model.addAttribute("brands", brands);


		// 回传用户选择的品牌id
		model.addAttribute("brandId", brandId);

		// 回传用户选择的价格
		model.addAttribute("pa", pa);
		model.addAttribute("pb", pb);

		// 构建已选条件的map
		TreeMap<String, String> map = new TreeMap<String, String>();

		if (brandId != null) {
			// 根据品牌id 获得品牌名称
			for (Brand brand : brands) {
				//注意写等号会有问题
				if (brandId == brand.getId()) {
					map.put("品牌", brand.getName());
					break;
				}
			}
		}

		// 价格
		if (pa != null && pb != null) {
			if (pb == -1) {
				map.put("价格", pa + "以上");
			} else {
				map.put("价格", pa + "-" + pb);
			}
		}

		// 回显已选条件
		model.addAttribute("map", map);
		System.err.println(map);
		return "search";
	}

}
