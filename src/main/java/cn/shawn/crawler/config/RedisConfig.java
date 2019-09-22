package cn.shawn.crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 描述:
 *
 * @author Shawn Liang
 * @create 2019-09-16 22:49
 * @package cn.shawn.crawler.config
 * @contact https://github.com/shawnliang1124
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisSerializer fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<Object>(Object.class);
    }


    /**
     * 使用自定义的fastjson序列化方式设置redis的序列化方式
     * @param redisConnectionFactory
     * @param fastJson2JsonRedisSerializer
     * @return
     * @throws Exception
     */
    @Bean
    public RedisTemplate initRedisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer fastJson2JsonRedisSerializer) throws Exception {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(fastJson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
