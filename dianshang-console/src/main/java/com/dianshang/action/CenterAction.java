package com.dianshang.action;

import com.dianshang.core.pojo.TestTb;
import com.dianshang.core.service.TestTbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * Created by 25131 on 2018/2/6.
 */
@Controller
public class CenterAction {
    @Autowired
    private TestTbService testTbService;
    /**
     * 将用户输入的url路径，取出关键部分，直接转发到指定jsp页面
     *
     * @param pageName
     * @return
     */
    // 总
    @RequestMapping(value = "console/{pageName}.do")
    public String consoleShow(@PathVariable(value = "pageName") String pageName) {
        return pageName;
    }

    // 框架页面
    @RequestMapping(value = "console/frame/{pageName}.do")
    public String consoleFrameShow(@PathVariable(value = "pageName") String pageName) {
        return "/frame/" + pageName;
    }

    // 商品
    @RequestMapping(value = "console/product/{pageName}.do")
    public String consoleProductShow(@PathVariable(value = "pageName") String pageName) {
        return "/product/" + pageName;
    }

    // 品牌
    @RequestMapping(value = "console/brand/{pageName}.do")
    public String consoleBrandShow(@PathVariable(value = "pageName") String pageName) {
        return "/brand/" + pageName;
    }

}
