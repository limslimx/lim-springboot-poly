package com.lim.poly.springboot.repository.weather;

import com.lim.poly.springboot.web.dto.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Repository
public class RedisWeatherRepository {

    private Logger log = Logger.getLogger(String.valueOf(this.getClass()));

    public final RedisTemplate<String, Object> redisDb;

    public boolean getExists(String key) throws Exception{
        log.info(this.getClass().getName() + ".getExists start!");
        log.info(this.getClass().getName() + ".getExists end!");
        return redisDb.hasKey(key);
    }

    public List<WeatherDto> getWeatherFromRedis(String key) throws Exception{
        log.info(this.getClass().getName() + ".getWeatherFromRedis start!");

        List<WeatherDto> weatherDtoList=null;
        redisDb.setKeySerializer(new StringRedisSerializer());
        redisDb.setValueSerializer(new Jackson2JsonRedisSerializer<>(WeatherDto.class));

        if (redisDb.hasKey(key)) {
            weatherDtoList = (List) redisDb.opsForList().range(key, 0, -1);
        }
        log.info(this.getClass().getName() + ".getWeatherFromRedis end!");
        return weatherDtoList;
    }

    public void setWeatherToRedis(String key, List<WeatherDto> weatherDtoList) throws Exception{
        log.info(this.getClass().getName() + ".setWeatherToRedis start!");

        redisDb.setKeySerializer(new StringRedisSerializer());
        redisDb.setValueSerializer(new Jackson2JsonRedisSerializer<>(WeatherDto.class));

        if (this.getExists(key)) {
            redisDb.delete(key);
        }

        Iterator<WeatherDto> it = weatherDtoList.iterator();
        while (it.hasNext()) {
            WeatherDto weatherDto = (WeatherDto) it.next();
            redisDb.opsForList().rightPush(key, weatherDto);
            weatherDto=null;
        }
        log.info(this.getClass().getName() + ".setWeatherToRedis end!");
    }

    public boolean setTimeOutHour(String roomKey, int hours) throws Exception{
        log.info(this.getClass().getName() + ".setTimeOutHour start!");
        log.info(this.getClass().getName() + ".setTimeOutHour end!");
        return redisDb.expire(roomKey, hours, TimeUnit.HOURS);
    }

}
