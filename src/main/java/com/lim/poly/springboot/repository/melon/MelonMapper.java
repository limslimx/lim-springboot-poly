package com.lim.poly.springboot.repository.melon;

import com.lim.poly.springboot.web.dto.MelonDto;
import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Component
public class MelonMapper {

    private Logger log = Logger.getLogger(String.valueOf(this.getClass()));
    private final MongoTemplate mongoTemplate;

    public boolean createCollection(String colNm) throws Exception {
        log.info(this.getClass().getName() + ".createCollcetion start!");

        boolean res = false;

        if (mongoTemplate.collectionExists(colNm)) {
            mongoTemplate.dropCollection(colNm);
        }

        mongoTemplate.createCollection(colNm);
        IndexOperations indexOperations = mongoTemplate.indexOps(colNm);
        indexOperations.ensureIndex(new Index("collect_time", Sort.Direction.ASC).on("rank", Sort.Direction.ASC).named("rankIdx"));

        res = true;

        log.info(this.getClass().getName() + ".createCollcetion end!");

        return res;
    }

    public int insertRank(List<MelonDto> pList, String colNm) throws Exception {
        log.info(this.getClass().getName() + ".insertRank Start!");

        int res = 0;

        if (pList == null) {
            pList = new ArrayList<MelonDto>();
        }

        Iterator<MelonDto> it = pList.iterator();

        while (it.hasNext()) {
            MelonDto pDTO = (MelonDto) it.next();

            if (pDTO == null) {
                pDTO = new MelonDto();
            }

            mongoTemplate.insert(pDTO, colNm);

        }

        res = 1;

        log.info(this.getClass().getName() + ".insertRank End!");

        return res;
    }

}
