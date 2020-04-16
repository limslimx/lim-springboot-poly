package com.lim.poly.springboot.service;

import com.lim.poly.springboot.web.dto.DataSerializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class GetSetService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public String test(){
        ValueOperations<String, Object> valueOperations=redisTemplate.opsForValue();
        DataSerializable setData=new DataSerializable();
        setData.setItemId("lim");
        setData.setSourceId("saemyung");

        valueOperations.set("key", setData);
        DataSerializable getData=(DataSerializable) valueOperations.get("key");
        System.out.println(getData.getItemId());
        System.out.println(getData.getSourceId());

        return getData.getSourceId();
    }
}
