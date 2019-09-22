package cn.shawn.crawler.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Filter;

/**
 * 描述:
 *
 * @author Shawn Liang
 * @create 2019-09-14 21:26
 * @package cn.shawn.crawler.utils
 * @contact https://github.com/shawnliang1124
 */

public class FileUtils {

    private static final String filePath = "/ljj/java-script/error.txt";

    private static final Logger logger = LoggerFactory.getLogger(Filter.class);

    public static void writeTxt(String msg){
        writeTxt(msg,null);
    }

    public static   void writeTxt(String msg,String path) {
        try {
            FileChannel outFc;
            if(StringUtils.isEmpty(path)){
                 outFc = new FileOutputStream(filePath,true).getChannel();
            }else{
                 outFc = new FileOutputStream(path,true).getChannel();
            }


            ByteBuffer byteBuffer = ByteBuffer.wrap(("\n"+msg).getBytes("UTF-8"));

            outFc.write(byteBuffer);

            outFc.close();
        }catch(Exception e) {
            logger.error("文件写入失败{}",e);
        }
    }

}
