package com.dianshang.action;

import com.dianshang.core.pojo.Brand;
import com.dianshang.core.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 品牌管理控制器
 *
 * @author Administrator
 */
@Controller
public class BrandAction {
    @Autowired
    private BrandService brandService;

    // 品牌
    @RequestMapping(value = "console/brand/{pageName}.do")
    public String consoleBrandShow(@PathVariable(value = "pageName") String pageName, Model model) {

        // 品牌查询
        if (pageName.equals("list")) {
            List<Brand> brands = brandService.findByExample(new Brand());
           // System.out.println(brands);
            System.out.println(brands.size());
            System.out.println(339);
            System.out.println(339);

            // 将查询的结果丢给页面显示
            model.addAttribute("brands", brands);
            System.out.println(model);
        }
        return "/brand/" + pageName;
    }


}
