package cn.shawn.crawler.controller;

import cn.shawn.crawler.producer.UrlProducer;
import cn.shawn.crawler.service.CompanyService;
import cn.shawn.crawler.service.GoodsService;
import cn.shawn.crawler.service.JsoupDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 *
 * @author Shawn Liang
 * @create 2019-09-14 12:26
 * @package cn.shawn.crawler.controller
 * @contact https://github.com/shawnliang1124
 */
@RestController
public class JsoupController {
    @Autowired
    private JsoupDemo jsoupDemo;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UrlProducer producer;

    @GetMapping("/")
    public String index(){
       return jsoupDemo.crawler();
    }

    @GetMapping("company")
    public String company(){
        return companyService.getCompanyId();
    }

    @GetMapping("goods")
    public String goods(){
        producer.produce();
        return"";
    }


}
