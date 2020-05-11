package com.lim.poly.springboot.repository.melon;

import com.lim.poly.springboot.web.dto.MelonDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MelonRepository extends MongoRepository<MelonDto, String> {
}
