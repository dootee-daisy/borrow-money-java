package com.dk.common.redis;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RedisService {

    /**
     * 永不过期
     */
    public static final long ETERNAL = TimeUnit.DAYS.toSeconds(365);
    /**
     * 30天过期
     */
    public static final long DEFAULT_EXPIRE = TimeUnit.DAYS.toSeconds(30);


    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    private ValueOperations valueOperations;
    @PostConstruct
    public void init() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        valueOperations = redisTemplate.opsForValue();
    }

    public <T> T getValue(String key,Class<T> clazz){
        JSON json = (JSON) valueOperations.get(key);
        if (json instanceof  JSONObject){
            return json.toJavaObject(clazz);
        }
        return null;
    }

    public String getStringValue(String key){
        return (String) valueOperations.get(key);
    }

    public <T> List<T> getListValue(String key, Class<T> clazz){
        JSON json = (JSON) valueOperations.get(key);
        if (json instanceof  JSONArray){
            return JSONArray.parseArray(json.toJSONString(),clazz);
        }
        return null;
    }

    public Long increment(String key, long value){
        return redisTemplate.opsForValue().increment(key,value);
    }

    public void addValue(String key,Object value, long time){
        valueOperations.set(key,value,time, TimeUnit.MINUTES);
    }

    public void addDefaultAgeValue(String key,Object value){
        valueOperations.set(key,value,DEFAULT_EXPIRE, TimeUnit.MINUTES);
    }

    /**
     * 添加不过期的key
     * @param key
     * @param value
     */
    public void addAgelessValue(String key,Object value){
        valueOperations.set(key,value);
    }

    public boolean hasKey(String key){
        return  redisTemplate.hasKey(key);
    }

    public void deleteKey(String key){
        redisTemplate.delete(key);
    }

    public void deleteKeys(String... keys){
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }
    public void deleteKeys(List<String> keys){
        redisTemplate.delete(keys);
    }

    /**
     * 设置key的生命周期
     *
     * @param key
     * @param time
     */
    public void expireKey(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.MINUTES);
    }

    /**
     * 指定key在指定的日期过期
     *
     * @param key
     * @param date
     */
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }
}
