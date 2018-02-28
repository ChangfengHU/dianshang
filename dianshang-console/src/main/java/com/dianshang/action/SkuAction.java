package com.dianshang.action;

import com.dianshang.core.pojo.Sku;
import com.dianshang.core.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品的库存类
 *
 * @author by ChangFeng
 * @Created by Administrator on 2017/83 0023 139.
 */
@Controller
public class SkuAction {
    @Autowired
    private SkuService skuService;
    @RequestMapping(value = "console/sku/list.do")
    public String consoleSkuShowList(Model model,String productId){
        System.err.println("进入了库存action");
        List<Sku> skus = skuService.findByProductId(productId);
        System.out.println("库存数量："+skus.size());
        model.addAttribute("skus", skus);

        return "/sku/list";
    }
    // 修改商品库存信息
    @RequestMapping(value = "console/sku/update.do")
    @ResponseBody
    public String consoleSkuDoUpdate(Sku sku) {
        int update = skuService.update(sku);
        return update + "";
    }
}
