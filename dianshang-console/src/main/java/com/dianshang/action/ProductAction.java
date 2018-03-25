package com.dianshang.action;

import com.dianshang.core.pojo.Product;
import com.dianshang.core.service.ProductService;
import com.dianshang.core.tools.Encoding;
import com.dianshang.core.tools.PageHelper;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

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
        int begin;
        int end;

        // 计算显示的起始页码（根据当前页码计算）：当前页码-5
        begin = pageProduct.getPageNum() - 5;
        if (begin < 1) {// 页码修复
            begin = 1;
        }
        // 计算显示的结束页码（根据开始页码计算）：开始页码+9
        end = begin + 9;
        if (end > pageProduct.getPages()) {// 页码修复
            end = pageProduct.getPages();
        }
        // 将查询出来的品牌集合传递给页面
        model.addAttribute("pageProduct", pageProduct);
        // 设置查询数据回显之将查询数据传回给页面
        model.addAttribute("name", name);
        model.addAttribute("isShow", isShow);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
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
    // 商品上架下架 isShow=1表示上架，=0表示下架
    @RequestMapping(value = "console/product/isShow.do")
    public String consoleProductDoIsShow(String ids, Integer isShow) throws IOException, SolrServerException {
        System.out.println(ids);
        System.out.println(isShow);

        // 设置修改结果，（是否上架）
        Product product = new Product();
        product.setIsShow(isShow);

        productService.update(product, ids);

        return "redirect:/console/product/list.do";
    }

}
