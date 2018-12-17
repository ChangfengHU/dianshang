package com.dianshang.core.action;

import com.dianshang.core.pojo.Product;
import com.dianshang.core.pojo.SuperPojo;
import com.dianshang.core.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台首页控制中心
 * 
 * @author Administrator
 *
 */
@Controller
public class ProductAction {

	@Autowired
	private ProductService productService;

	// 显示单个商品页面
	@RequestMapping(value = "/product/detail.do")
	public String showSingleProduct(Model model, Long productId) {
		System.out.println("商品id:" + productId);
		SuperPojo superPojo = productService.findById(productId);

		Product product = (Product) superPojo.get("product");
		List<SuperPojo> skus = (List<SuperPojo>) superPojo.get("skus");

		System.out.println("商品名称：" + product.getName());
		System.out.println("sku数量：" + skus.size());


		// 去除颜色重复
		Map<Long, String> colors = new HashMap<Long, String>();

		for (SuperPojo sku : skus) {
			// 将颜色添加到hm集合中，利用hm集合来去除重复   key是颜色的id  value是颜色名称
			colors.put((Long) sku.get("color_id"), (String) sku.get("name"));
		}
		// 反正万能实体对象要被传递，将非重复的颜色对象也通过superPojo顺便传递到页面
		superPojo.setProperty("colors", colors);
		// 将商品对象和该商品的库存对象的集合(万能pojo对象)传递给页面
		model.addAttribute("superPojo", superPojo);

		return "product";
	}

}
