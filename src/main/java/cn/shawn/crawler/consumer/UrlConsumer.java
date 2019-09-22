package cn.shawn.crawler.consumer;

import cn.shawn.crawler.dao.GoodsSpuDao;
import cn.shawn.crawler.entity.FhGoodsSpuEntity;
import cn.shawn.crawler.entity.Test;
import cn.shawn.crawler.utils.FileUtils;
import cn.shawn.crawler.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述:
 *
 * @author Shawn Liang
 * @create 2019-09-15 19:54
 * @package cn.shawn.crawler.consumer
 * @contact https://github.com/shawnliang1124
 */
@Component
public class UrlConsumer {
    private static final Logger logger = LoggerFactory.getLogger(UrlConsumer.class);

    @Autowired
    private ConcurrentLinkedQueue<Test> testQueue;

    private static final String suffix = "product.html";

   private static final Pattern PATTERN = Pattern.compile("(\\d+)");

   private static final String BASE_URL = "http://www.jc35.com";




    public void consumer() {


    }



    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://www.jc35.com/st130439/product_1724504.html").get();
        String picUrls = doc.select("#productPhoto").select("tbody").select("a[href]").attr("href");

        //获取产品信息
        //获取产品信息
        String goodsName = doc.select(".productName").select("h1").text();
        Element ul = doc.select(".proDetail0").select("ul").first();
        Elements li = ul.select("li");
        String spuModel = li.eq(0).select("b").first().text();
        String brand = li.eq(1).select("b").first().text();
        String producer = li.eq(2).select("b").first().text();
        String address = li.eq(3).select("b").first().text();
        //获取产品简介
        Elements paramters = doc.select(".productParameter");
        Elements ths = paramters.select("table").select("th");
        Elements tds = paramters.select("table").select("td");
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < ths.size(); i++) {
            jsonObject.put(ths.get(i).text(),tds.get(i).text());
        }
        Elements text = doc.select(".pdc_box").first().select(".text").select("div").remove();

        System.out.println(goodsName+"--"+spuModel);
        System.out.println(JSON.toJSONString(jsonObject));
        System.out.println(text.text());

        // String pageText = doc.select(".newspages").select(".jump").text();
      

//        Matcher m= p.matcher(pageText);
//        while(m.find(0)){
//            System.out.println(m.group(1));
//            break;
//        }
      //  System.out.println(pageText);
    }

    
}
