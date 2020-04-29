package com.lim.poly.springboot.repository.movie;

import com.lim.poly.springboot.web.dto.MovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Repository
public class RedisMovieRepository {

    private Logger log=Logger.getLogger(String.valueOf(this.getClass()));

    @Autowired
    public RedisTemplate<String, Object> redisDb;

    public boolean getExists(String key) throws Exception{
        log.info(this.getClass().getName()+".getExists start!");
        return redisDb.hasKey(key);
    }

    public List<MovieDto> getMovieRank(String key) throws Exception{
        log.info(this.getClass().getName() + ".getMovieRank start!");

        List<MovieDto> movieDtoList=null;

        redisDb.setKeySerializer(new StringRedisSerializer());
        redisDb.setValueSerializer(new Jackson2JsonRedisSerializer<>(MovieDto.class));

        if(redisDb.hasKey(key)){
            movieDtoList=(List)redisDb.opsForList().range(key, 0, -1);
        }
        log.info(this.getClass().getName() + ".getMovieRank end!");
        return movieDtoList;
    }

    public int setMovieRank(String key, List<MovieDto> movieDtoList) throws Exception{
        log.info(this.getClass().getName() + ".setMovieRank start!");

        int res=0;

        redisDb.setKeySerializer(new StringRedisSerializer());
        redisDb.setValueSerializer(new Jackson2JsonRedisSerializer<>(MovieDto.class));

        if(this.getExists(key)){
            redisDb.delete(key);
        }

        Iterator<MovieDto> it = movieDtoList.iterator();
        while (it.hasNext()){
            MovieDto movieDto = (MovieDto) it.next();
            redisDb.opsForList().rightPush(key, movieDto);
            movieDto = null;
        }
        res=1;
        log.info(this.getClass().getName() + ".setMovieRank end!");
        return res;
    }

    public boolean setTimeOutHour(String roomKey, int hours) throws Exception{
        log.info(this.getClass().getName() + ".setTimeHour start!");
        log.info(this.getClass().getName() + ".setTimeHour end!");
        return redisDb.expire(roomKey, hours, TimeUnit.HOURS);
    }

}
