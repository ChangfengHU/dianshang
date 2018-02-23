package com.dianshang.action;

import com.dianshang.core.pojo.Brand;
import com.dianshang.core.service.BrandService;
import com.dianshang.core.tools.Encoding;
import com.dianshang.core.tools.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.sun.org.apache.xml.internal.serialize.OutputFormat.Defaults.Encoding;

/**
 * 品牌管理控制器
 *
 * @author Administrator
 */
@Controller
public class BrandAction {
    @Autowired
    private BrandService brandService;

    // 品牌1
    @RequestMapping(value = "console/brand/{pageName}.do")
    public String consoleBrandShow(@PathVariable(value = "pageName") String pageName,
                                   Model model,String name, Integer isDisplay, Integer pageNum, Integer pageSize
    ) {

        // 品牌查询
        if (pageName.equals("list")) {

            // 设置查询条件
            Brand brand = new Brand();
            brand.setName(name);
            brand.setIsDisplay(isDisplay);

            // 开始分页查询
            PageHelper.Page<Brand> pageBrand = brandService.findByExample(brand, pageNum,
                    pageSize);
            System.err.println("总数据:"+pageBrand.getTotal());
            System.err.println("pageSize:"+pageBrand.getPageSize());
            // 将查询出来的品牌集合传递给页面
            int begin;
            int end;

            // 计算显示的起始页码（根据当前页码计算）：当前页码-5
            begin = pageBrand.getPageNum() - 5;
            if (begin < 1) {// 页码修复
                begin = 1;
            }
            // 计算显示的结束页码（根据开始页码计算）：开始页码+9
            end = begin + 9;
            if (end > pageBrand.getPages()) {// 页码修复
                end = pageBrand.getPages();
            }
            model.addAttribute("pageBrand", pageBrand);

            // 设置查询数据回显之将查询数据传回给页面
            model.addAttribute("name", name);
            model.addAttribute("isDisplay", isDisplay);
            model.addAttribute("begin", begin);
            model.addAttribute("end", end);

        }
        return "/brand/" + pageName;
    }
    // 显示修改页面（具体映射）
    @RequestMapping(value = "console/brand/showedit.do")
    public String consoleBrandShowEdit(Long brandId, Model model) {
        // 获得要修改品牌的id
        System.err.println("显示id:"+brandId);
        // 设置修改的数据回显
        Brand brand = brandService.findById(brandId);
        System.err.println(brand);
        model.addAttribute("brand", brand);
        return "/brand/edit";
    }
    // 执行品牌修改
    @RequestMapping(value = "console/brand/doEdit.do")
    public String consoleBrandDoEdit(Brand brand) {

        // 获得要修改的对象内容
        System.err.println("修改前先去看看品牌数据"+brand);
        // 根据id来执行修改
        brandService.updateById(brand);
        // 重定向到显示品牌列表功能页面
        return "redirect:/console/brand/list.do";
    }

    // 品牌删除
    @RequestMapping(value = "console/brand/doDelete.do")
    public String consoleBrandDoDelete(String brandId, String name,
                                       Integer isDisplay, Integer pageNum) {
        System.err.println("看看页面传过来的brandId"+brandId);
        brandService.deleteById(brandId);
        System.err.println(name);
        System.err.println(isDisplay);
        return "redirect:/console/brand/list.do?name=" + com.dianshang.core.tools.Encoding.encodeGetRequest(name);
       /* return "redirect:/console/brand/list.do?name=" + name + "&isDisplay="
                + isDisplay + "&pageNum=" + pageNum;*/
    }
     ///品牌删除
    @RequestMapping(value = "console/brand/doDeletes.do", method = RequestMethod.POST )
    public String consoleBrandDoDeletes(String ids, String name,
                                       Integer isDisplay, Integer pageNum) {
        System.err.println("看看页面传过来的brandIds"+ids);
        brandService.deleteByIds(ids);
        return "redirect:/console/brand/list.do";
    }



}
