package com.dianshang.action;

import com.dianshang.core.pojo.Product;
import com.dianshang.core.service.ProductService;
import com.dianshang.core.tools.Encoding;
import com.dianshang.core.tools.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 25131 on 2018/2/25.
 */
@Controller
public class ProductAction {
    @Autowired(required=true)
    private ProductService productService;
    // 显示商品列表（具体映射）
    @RequestMapping(value = "console/product/list.do")
    public String consoleProductShowList(Model model, String name, Long brandId,
                                         Integer isShow, Integer pageNum, Integer pageSize) {

        // 设置查询条件
        // 只演示查name，其它条件省略掉了，如果是get方式，注意处理乱码
        Product product = new Product();
        System.err.println(name);
        //name = Encoding.encodeGetRequest(name);
        product.setName(name);

        PageHelper.Page<Product> pageProduct = productService.findByExample(product, pageNum,
                pageSize);
        System.err.println(pageProduct);
        // 将查询出来的品牌集合传递给页面
        model.addAttribute("pageProduct", pageProduct);
        // 设置查询数据回显之将查询数据传回给页面
        model.addAttribute("name", name);
        model.addAttribute("isShow", isShow);
        // model.addAttribute("brandId", brandId);

        return "/product/list";
    }
    // 显示商品添加（具体映射）
    @RequestMapping(value = "console/product/showAdd.do")
    public String consoleBrandShowAdd(Model model) {
        // 加载所有可用颜色
        model.addAttribute("colors", productService.findEnableColors());
        model.addAttribute("brands", productService.findEnablebrands());
        return "/product/add";
    }
    // 添加商品
    @RequestMapping(value = "console/product/doAdd.do")
    public String consoleProductDoAdd(Model model,Product product) {
        productService.add(product);
        return "redirect:/console/product/list.do";
    }

}
