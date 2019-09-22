package cn.shawn.crawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 *
 * @author Shawn Liang
 * @create 2019-09-22 12:04
 * @package cn.shawn.crawler.controller
 * @contact https://github.com/shawnliang1124
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RedisTemplate initRedisTemplate;

    @GetMapping("/redis")
    public String test(){
        SetOperations setOperations = initRedisTemplate.opsForSet();
        setOperations.add("测试","测试123");
        setOperations.add("测试","测试234");
        setOperations.add("测试","测试567");
        return "";
    }
}
