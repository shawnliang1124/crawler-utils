package cn.shawn.crawler.service;

import cn.shawn.crawler.consumer.UrlConsumer;
import cn.shawn.crawler.producer.UrlProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

/**
 * 描述:
 * 商品详情数据落表
 *
 * @author Shawn Liang
 * @create 2019-09-15 12:46
 * @package cn.shawn.crawler.service
 * @contact https://github.com/shawnliang1124
 */
@Service
public class GoodsService {
    @Autowired
    private UrlConsumer consumer;

    @Autowired
    private UrlProducer producer;




    public String getGoods(){
        producer.produce();
        for (int i = 0; i < 12; i++) {
            consumer.consumer();
        }
        return "";
    }

}
