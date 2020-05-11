package com.lim.poly.springboot.repository.melon;

import com.lim.poly.springboot.domain.melon.Melon;

public interface MelonRepository extends org.springframework.data.mongodb.repository.MongoRepository<Melon, String> {
}
