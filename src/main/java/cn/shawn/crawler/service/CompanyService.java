package cn.shawn.crawler.service;

import cn.shawn.crawler.dao.TestDao;
import cn.shawn.crawler.entity.Test;
import cn.shawn.crawler.utils.FileUtils;
import cn.shawn.crawler.utils.HttpUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

/**
 * 描述:
 * 获取厂家的ID
 *
 * @author Shawn Liang
 * @create 2019-09-14 14:59
 * @package cn.shawn.crawler.service
 * @contact https://github.com/shawnliang1124
 */
@Service
public class CompanyService {

    @Autowired
    private TestDao testDao;

    @Autowired
    private ExecutorService getThreadPool;

    @Autowired
    private BlockingQueue<Runnable> blockingQueue;

    @Autowired
    private ConcurrentLinkedQueue<Integer> concurrentLinkedQueue;



    private static Logger logger = LoggerFactory.getLogger(CompanyService.class);









    public String getCompanyId(){
        int num = 100;
        for (int i = 0; i < 10; i++) {
            FactoryDemo factoryDemo = new FactoryDemo(concurrentLinkedQueue,testDao);
            getThreadPool.execute(factoryDemo);
        }
        //getThreadPool.shutdown();
        while(getThreadPool.isShutdown()){
            logger.info("任务结束！！");
        }
        return "";
    }


}

class FactoryDemo implements Runnable{



    private static Logger logger = LoggerFactory.getLogger(FactoryDemo.class);

    private static final String URL = "http://www.jc35.com/st*/";

    private ConcurrentLinkedQueue<Integer> queue;
    private TestDao testDao;

    public FactoryDemo( ConcurrentLinkedQueue<Integer> concurrentLinkedQueue,TestDao testDao){
        this.queue = concurrentLinkedQueue;
        this.testDao = testDao;
    }
    @Override
    public void run() {
        int count = 0;
        while (!queue.isEmpty()){
            Integer num = queue.poll();
            String replace = URL.replace("*",String.valueOf(num.intValue()));
            logger.info("线程地址{} ,url 地址是:{}",Thread.currentThread().getName(),replace);
            JSONObject result = HttpUtils.httpGet(replace);
            if(result != null){
                Test test = new Test(num + "", replace);

                testDao.save(test);
                logger.info("id{}成功保存",num);
            }else{
                logger.error("id{}店铺为空",num);
            }
            count++;
            if(count % 5 == 0){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                   logger.error("休眠异常{}",e);
                }
            }
        }

    }
}
