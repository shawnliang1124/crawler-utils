package cn.shawn.crawler.service;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 描述:
 *
 * @author Shawn Liang
 * @create 2019-09-14 11:47
 * @package cn.shawn.crawler.service
 * @contact https://github.com/shawnliang1124
 */
@Service
public class JsoupDemo {
    /**
     * 分页
     */
    private static final int PAGE = 2;

    private static final String URL = "http://www.jc35.com/chanpin-0_p*.html";

    private static Logger logger = LoggerFactory.getLogger(JsoupDemo.class);

    @Autowired
    private HttpGet httpGet;


    public String crawler(){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet();
        int i = 1;
        while (i <= PAGE){
            String replace = URL.replace("*",String.valueOf(i));
            logger.info("url 地址是:{}",replace);
            try {
                httpGet.setURI(new URI(replace));
                httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
            } catch (URISyntaxException e) {
                logger.error("url转换出错，{}",e);
            }

            try ( CloseableHttpResponse response = client.execute(httpGet);){

                int code = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                if(code == 200){
                    String html = EntityUtils.toString(entity, Consts.UTF_8);
                    Document doc = Jsoup.parse(html);
                    Element ulList = doc.getElementsByClass("productList").first();
                    Elements elements = ulList.select("div[class='item']");
                    elements.forEach(element -> {
                        String text = element.getElementsByTag("p").text();
                        System.out.println(text);
                    });
                }
                EntityUtils.consume(response.getEntity());
            } catch (IOException e) {
                logger.error("执行http请求异常，{}",e);
            }
            i++;
            logger.info("i的值是{}",i);
        }
        return "";
    }



}
