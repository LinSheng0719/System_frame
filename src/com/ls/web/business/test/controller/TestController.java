package com.ls.web.business.test.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author linsheng
 * @version V1.0
 * @Title: TestController.java
 * @Package com.ls.business.test.controller
 * @Description:
 * @email hi.linsheng@foxmail.com
 * @date 2016年5月13日 上午9:25:25
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping()
    public String page(ModelMap model) {
        model.addAttribute("page", "page");
        return "page";
    }

    @RequestMapping("/page1")
    public String page1(Model model) {
        System.out.printf("123");
        return "page";
    }

    @RequestMapping(value = "/page2", method = RequestMethod.POST)
    public String page2(@MatrixVariable Map<String, String> matrixVars) {
        System.out.println(matrixVars);
        return "page";
    }

    @RequestMapping(value = "/page3")
    public Map<String, Object> page3() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "lin");
        map.put("age", "23");
        System.out.println(map);
        return map;
    }

    @RequestMapping("/page4")
    public void page4() {
        System.out.println("page4");
    }

    @RequestMapping("/page5")
    public ModelMap page5() {
        System.out.println(123);
        return new ModelMap("name", "lin");
    }

    @RequestMapping(value = "/page6/{ownerId}", method = RequestMethod.GET)
    public String findOwner(@PathVariable String ownerId, Model model) {
        try {
            System.out.println(URLDecoder.decode(ownerId, "GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.addAttribute("id", ownerId);
        return "page";
    }
}
