package com.lim.poly.springboot.repository.chat;

import com.lim.poly.springboot.util.CmmUtil;
import com.lim.poly.springboot.web.dto.ChatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Repository
public class ChatRepository implements IChatMapper, IContRedis {

    private final Logger log=Logger.getLogger(String.valueOf(this.getClass()));

    @Autowired
    public RedisTemplate<String, Object> redisDb;

    public Set<String> getRoomList() throws Exception{

        log.info(this.getClass().getName()+".getRoomList() Start!!");

        redisDb.setKeySerializer(new StringRedisSerializer());
        redisDb.setValueSerializer(new StringRedisSerializer());

        Set<String> rSet=(Set)redisDb.keys("Chat*");

        log.info(this.getClass().getName()+".getRoomList() End!!");

        return rSet;
    }

    public int insertChat(ChatDto chatDto) throws Exception{
        log.info(this.getClass().getName()+".insertChat() Start!!");

        int res=0;

        String roomKey= CmmUtil.nvl(chatDto.getRoomKey());

        redisDb.setKeySerializer(new StringRedisSerializer());
        redisDb.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatDto.class));

        redisDb.opsForList().leftPush(roomKey, chatDto);

        res=1;

        log.info(this.getClass().getName()+".insertChat() End!!");

        return res;
    }

    public List<ChatDto> getChat(ChatDto chatDto) throws Exception{

        log.info(this.getClass().getName()+".getChat() Start!!");

        List<ChatDto> rList=null;
        String roomKey=CmmUtil.nvl(chatDto.getRoomKey());

        redisDb.setKeySerializer(new StringRedisSerializer());
        redisDb.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatDto.class));

        if(redisDb.hasKey(roomKey)){
            rList=(List)redisDb.opsForList().range(roomKey, 0, -1);

            if(rList==null){
                rList=new LinkedList<ChatDto>();
            }
        }

        log.info(this.getClass().getName()+".getChat() End!!");

        return rList;
    }

    public boolean setTimeOutHour(String roomKey, int hours) throws Exception{
        log.info(this.getClass().getName()+".setTimeOutHour() Start!!");
        return redisDb.expire(roomKey, hours, TimeUnit.HOURS);
    }

    public boolean setTimeOutMinute(String roomKey, int minutes) throws Exception{
        log.info(this.getClass().getName()+".setTimeOutMinute() Start!!");
        return redisDb.expire(roomKey, minutes, TimeUnit.MINUTES);
    }
}
