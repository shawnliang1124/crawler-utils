package cn.shawn.crawler.producer;

import cn.shawn.crawler.consumer.UrlConsumer;
import cn.shawn.crawler.dao.GoodsSpuDao;
import cn.shawn.crawler.dao.TestDao;
import cn.shawn.crawler.entity.FhGoodsSpuEntity;
import cn.shawn.crawler.entity.Test;
import cn.shawn.crawler.utils.FileUtils;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述:
 * 生产者消费
 * 分页查询，每次取100个对象放入
 * @author Shawn Liang
 * @create 2019-09-15 19:20
 * @package cn.shawn.crawler.producer
 * @contact https://github.com/shawnliang1124
 */
@Component
public class UrlProducer{
    @Autowired
    private TestDao testDao;

    @Value("${pageSize}")
    private int pageSize;

    @Value("${page.end}")
    private int end;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsSpuDao goodsSpuDao;

    @Value("${error.path}")
    private String errorPath;

    @Autowired
    @Qualifier("crawlerPool")
    private ExecutorService crawlerPool;

    @Autowired
   private ConcurrentLinkedQueue<Test> testQueue;

    public void produce(){
     List<Test> all = testDao.findAllIdBetween(1,end);
     all.forEach(a ->{
         testQueue.add(a);
     });
        for (int i = 0; i < 12; i++) {
            crawlerPool.execute(new Consumer(redisTemplate,goodsSpuDao,errorPath,testQueue));

        }
    }




}

class Consumer implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private static final String suffix = "product.html";

    private static final Pattern PATTERN = Pattern.compile("(\\d+)");

    private static final String BASE_URL = "http://www.jc35.com";

    private RedisTemplate initRedisTemplate;

    private GoodsSpuDao goodsSpuDao;

    private String errorPath;

    private ConcurrentLinkedQueue<Test> testQueue;

    public Consumer(RedisTemplate redisTemplate, GoodsSpuDao goodsSpuDao, String errorPath,ConcurrentLinkedQueue<Test> testQueue) {
        this.initRedisTemplate = redisTemplate;
        this.goodsSpuDao = goodsSpuDao;
        this.errorPath = errorPath;
        this.testQueue = testQueue;
    }
    /**
     * 循环页面上的doc list列表获取商品详情
     * @param page
     * @param doc
     */
    private void cycleGoodsInfo(int page,Document doc) {
        int i = 1;
        while(i <= page){
            Elements liList = doc.select(".pdc_list").select("ul").select("li");
            Elements li2 = doc.select(".rightContent").select(".product").select("li");
            if(!liList.isEmpty()){
                liList.forEach(li ->{
                    String href = li.select("a[href]").first().attr("href");
                    getGoodsSpu(href);
                });
            }else if(!li2.isEmpty()){
                li2.forEach(li ->{
                    String href = li.select("a[href]").first().attr("href");
                    getGoodsSpu(href);
                });
            }

            i++;
        }

    }

    /**
     * 发送spu的商品请求，获取商品spu信息
     * @param href
     */
    private  void getGoodsSpu(String href) {
        String fullUrl = BASE_URL+href;
        try {
            Document doc = Jsoup.connect(fullUrl).get();
            //获取图片地址
            String picUrls = doc.select("#productPhoto").select("tbody").select("a[href]").attr("href");

            //获取产品信息
            String goodsName = doc.select(".productName").select("h1").text();
            Element ul = doc.select(".proDetail0").select("ul").first();
            Elements li = ul.select("li");
            String spuModel = li.eq(0).select("b").first().text();
            String brand = li.eq(1).select("b").first().text();
            String producer = li.eq(2).select("b").first().text();
            String address = li.eq(3).select("b").first().text();
            //获取产品简介
            Elements ths = doc.select(".productParameter").select("table").select("th");
            Elements tds = doc.select(".productParameter").select("table").select("td");
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < ths.size(); i++) {
                String key = ths.get(i).text();
                String value = tds.get(i).text();
                jsonObject.put(key, value);
                initRedisTemplate.opsForSet().add("keys", key);
                initRedisTemplate.opsForSet().add(key, value);
            }
            //详细的文字
            Element pdcBox = doc.select(".pdc_box").first();
            Element about = doc.select(".aboutUsText").first();
            String words = "";
            if(pdcBox!= null){
                words = pdcBox.select(".text").select("div").remove().text();
            }else if(about!=null){
               words =  about.select("div").remove().text();
            }

            //保存数据到数据库当中
            FhGoodsSpuEntity entity = new FhGoodsSpuEntity();
            entity.setSpuList(JSON.toJSONString(jsonObject));
            entity.setSpuName(goodsName);
            entity.setSpuModel(spuModel);
            entity.setPicUrls(picUrls);
            entity.setAddress(address);
            entity.setProducer(producer);
            entity.setDescription(words);
            entity.setUrl(fullUrl);
            entity.setSpuPrice(randomDecimal());
            entity.setQuantity(stock());
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            entity.setCreateTime(ts);
            initRedisTemplate.opsForSet().add("brands",brand);
            logger.info("商品spu保存成功,url 是{}",fullUrl,entity.toString());
            goodsSpuDao.save(entity);
        } catch (IOException e) {
            logger.error("请求商品spu页面失败，url is{},异常打印{}",fullUrl,e);
            FileUtils.writeTxt(fullUrl,errorPath);
        }
    }

    /**
     * 随机生成价格
     * @return
     */
    private BigDecimal randomDecimal(){
        int i = (int) (1 + Math.random() * (99999));
        return new BigDecimal(i);
    }

    /**
     * 随机生成库存
     * @return
     */
    private int stock(){
        int i = (int) (1 + Math.random() * (9999));
        return i;
    }

    @Override
    public void run() {
        while (!testQueue.isEmpty()){
            Test test = testQueue.poll();
            if(test != null){
                String url = test.getContent() + suffix;
                try {
                    Document doc = Jsoup.connect(url).get();
                    String pageText = doc.select(".jump").text();
                    if(StringUtil.isBlank(pageText)){
                        logger.info("未找到商品信息,url is {}",url);
                        continue;
                    }
                    logger.info("找到商品信息,url is {}",url);
                    int page = 0;
                    Matcher matcher = PATTERN.matcher(pageText);
                    while (matcher.find(0)){
                        String group = matcher.group(1).toString();
                        page = Integer.valueOf(group).intValue();
                        break;
                    }
                    //循环遍历商品详情页
                    cycleGoodsInfo(page,doc);
                } catch (IOException e) {
                    logger.error("url请求失败,url is {} ,异常信息是{}", url,e);
                    FileUtils.writeTxt(url,errorPath);
                }
            }

        }
    }
}
