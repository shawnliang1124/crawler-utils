package cn.shawn.crawler;

import cn.shawn.crawler.entity.Test;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * *  @author jiajing.liang
 * *  @date 2019/9/11 12:50
 * *  @description
 */
@SpringBootApplication
@EnableAsync
@EnableJpaRepositories("cn.shawn.crawler.dao")
public class CrawlerApp {
    public static void main(String[] args) {
        SpringApplication.run(CrawlerApp.class,args);
    }

    @Value("${id.start}")
    private String start;
    @Bean
    public HttpGet httpGet(){
        return new HttpGet();
    }
    @Value("${id.end}")
    private String idEnd;
    @Bean
    @Lazy
    public ExecutorService getThreadPool(BlockingQueue<Runnable> blockingQueue){

        return
        new ThreadPoolExecutor(4,6,5, TimeUnit.SECONDS,blockingQueue );

    }

    @Bean
   public BlockingQueue<Runnable> blockingQueue(){
       BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(4);
       return queue;
   }

   @Bean
   public ConcurrentLinkedQueue<Integer> concurrentLinkedQueue(){
       ConcurrentLinkedQueue<Integer> integers = new ConcurrentLinkedQueue<>();
       int i = Integer.valueOf(start).intValue();
       int max = Integer.valueOf(idEnd);
       while (i < max){
           integers.add(i);
           i++;
       }
       return integers;
   }

   @Bean
   public ConcurrentLinkedQueue<Test> testQueue(){
       return new ConcurrentLinkedQueue<>();
   }

    @Bean
    @Lazy
    public ExecutorService crawlerPool(){
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(4);
        return
                new ThreadPoolExecutor(6,8,5, TimeUnit.SECONDS,queue );

    }

    @Bean
    public Executor myTaskAsyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(6);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(4);
        executor.setKeepAliveSeconds(5);
        executor.setThreadNamePrefix("MyExecutor-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}



